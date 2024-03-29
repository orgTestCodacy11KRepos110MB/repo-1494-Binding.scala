package com.thoughtworks.binding
package html

private[binding] object Macros:
  import scala.quoted.Type
  import scala.quoted.Expr
  import scala.quoted.Quotes
  import java.io.Reader
  import net.sourceforge.htmlunit.cyberneko.HTMLConfiguration
  import net.sourceforge.htmlunit.cyberneko.HTMLScanner
  import net.sourceforge.htmlunit.cyberneko.parsers.DOMFragmentParser
  import org.apache.xerces.xni.parser.XMLInputSource
  import java.io.StringReader
  import org.w3c.dom.DocumentFragment
  import net.sourceforge.htmlunit.cyberneko.parsers.DOMParser
  import org.apache.xerces.xni.XMLAttributes
  import org.apache.xerces.xni.Augmentations
  import org.apache.xerces.xni.QName
  import net.sourceforge.htmlunit.cyberneko.HTMLEventInfo
  import net.sourceforge.htmlunit.cyberneko.HTMLScanner
  import org.apache.xerces.util.XMLStringBuffer
  import org.apache.xerces.xni.XMLString
  import java.io.CharArrayReader
  import java.io.PipedReader
  import java.io.PipedWriter
  import org.xml.sax.InputSource
  import scala.collection.Searching
  import org.apache.xerces.util.XMLAttributesImpl
  import net.sourceforge.htmlunit.cyberneko.HTMLAugmentations
  import org.apache.xml.serialize.XMLSerializer
  import org.w3c.dom.ls.LSSerializer
  import org.w3c.dom.ls.DOMImplementationLS
  import Binding.BindingSeq
  import org.w3c.dom.Node
  import scala.collection.View
  import scala.scalajs.js.`import`
  import scala.concurrent.ExecutionContext
  import scala.concurrent.Future
  import org.w3c.dom.Element
  import org.w3c.dom.Text
  import scalaz.Nondeterminism
  import scalaz.Monad
  import org.w3c.dom.NodeList
  import org.w3c.dom.Comment
  import scala.quoted.Varargs
  import com.thoughtworks.dsl.macros.Reset
  import org.w3c.dom.Attr
  import scala.util.chaining.given
  import com.thoughtworks.binding.CovariantStreamT
  import com.thoughtworks.binding.html.Definitions
  import com.thoughtworks.binding.bindable.BindableSeq
  import org.w3c.dom.NamedNodeMap
  import com.thoughtworks.dsl.Dsl
  import org.w3c.dom.Text
  import org.w3c.dom.CDATASection
  import org.w3c.dom.ProcessingInstruction
  import scala.collection.IndexedSeqView
  import scala.collection.immutable.BitSet

  val Placeholder = "\"\""
  val ElementArgumentUserDataKey = "Binding.scala element argument"
  val AttributeArgumentsUserDataKey =
    "Binding.scala attribute arguments"
  val AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations"
  val SYNTHESIZED_ITEM =
    HTMLEventInfo.SynthesizedItem();
  def parseHtmlParts(using Quotes)(
      parts: IndexedSeq[String],
      argExprs: Seq[Expr[Any]]
  ) =
    import scala.quoted.quotes.reflect.asTerm
    import scala.quoted.quotes.reflect.report
    val html = parts.mkString(Placeholder)
    val partOffsets = parts.view
      .flatMap { part =>
        Seq(part.length, Placeholder.length)
      }
      .scanLeft(0)(_ + _)
      .dropRight(1)
      .toIndexedSeq
    val document = org.apache.html.dom.HTMLDocumentImpl()
    val fragment = document.createDocumentFragment()
    val parser = new DOMFragmentParser:
      fParserConfiguration
        .asInstanceOf[HTMLConfiguration]
        .setFeature(AUGMENTATIONS, true);
      override def startElement(
          element: QName,
          attrs: XMLAttributes,
          augs: Augmentations
      ): Unit =
        val dynamicAttributeIndices =
          (0 until attrs.getLength).view
            .collect(Function.unlift { i =>
              val htmlEventInfo = attrs
                .getAugmentations(i)
                .getItem(AUGMENTATIONS)
                .asInstanceOf[HTMLEventInfo]
              val beginCharacterOffset = htmlEventInfo.getBeginCharacterOffset
              val endCharacterOffset = htmlEventInfo.getEndCharacterOffset
              val beginSearchResult =
                partOffsets.search(beginCharacterOffset)
              val endSearchResult =
                partOffsets.search(endCharacterOffset)
              val beginIndex = beginSearchResult.insertionPoint
              val endIndex = endSearchResult.insertionPoint
              if beginIndex % 2 == 1 && endIndex == beginIndex + 1 then
                Some(i -> (beginIndex / 2))
              else None
            })
            .toMap
        val staticAttributes = XMLAttributesImpl()
        for i <- 0 until attrs.getLength do
          if !dynamicAttributeIndices.contains(i) then
            val qName = QName()
            attrs.getName(i, /* out */ qName)
            staticAttributes.addAttribute(
              qName,
              attrs.getType(i),
              attrs.getValue(i)
            )
        super.startElement(element, staticAttributes, augs)
        fCurrentNode.setUserData(
          AttributeArgumentsUserDataKey,
          for (i, arg) <- dynamicAttributeIndices
          yield
            if attrs.getValue(i) != "" then
              report.error(
                "String interpolation must be the whole attribute value, not a part of the attribute value.",
                argExprs(arg).asTerm.pos
              )
            val qName = QName()
            attrs.getName(i, /* out */ qName)
            qName -> arg
          ,
          null
        )
      private var isProcessingCharacters =  false
      override def characters(text: XMLString, augs: Augmentations): Unit =
        if isProcessingCharacters then super.characters(text, augs)
        else
          isProcessingCharacters = true
          try if augs == null then super.characters(text, augs)
          else
            val htmlEventInfo =
              augs.getItem(AUGMENTATIONS).asInstanceOf[HTMLEventInfo]
            val beginCharacterOffset = htmlEventInfo.getBeginCharacterOffset
            val endCharacterOffset = htmlEventInfo.getEndCharacterOffset
            val beginSearchResult =
              partOffsets.search(beginCharacterOffset)
            val endSearchResult =
              partOffsets.search(endCharacterOffset)
            val beginIndex = beginSearchResult.insertionPoint
            val endIndex = endSearchResult.insertionPoint
            if beginIndex == endIndex || (beginIndex % 2 == 0 && endIndex == beginIndex + 1) then
              super.characters(text, augs)
            else
              def partLoop(index: Int): Unit =
                assert(index % 2 == 0)
                endSearchResult match {
                  case Searching.InsertionPoint(`endIndex`)
                      if endIndex == index + 1 =>
                    fParserConfiguration
                      .asInstanceOf[HTMLConfiguration]
                      .evaluateInputSource(
                        XMLInputSource(
                          null,
                          null,
                          null,
                          StringReader(
                            html.substring(
                              partOffsets(index),
                              endCharacterOffset
                            )
                          ),
                          null
                        )
                      )
                  case _ =>
                    fParserConfiguration
                      .asInstanceOf[HTMLConfiguration]
                      .evaluateInputSource(
                        XMLInputSource(
                          null,
                          null,
                          null,
                          StringReader(
                            parts(index / 2)
                          ),
                          null
                        )
                      )
                    argLoop(index + 1)
                }
              end partLoop

              def argLoop(index: Int): Unit =
                assert(index % 2 == 1)
                if (endIndex > index) {
                  val comment = document.createComment("")
                  comment.setUserData(
                    ElementArgumentUserDataKey,
                    index / 2,
                    null
                  )
                  fCurrentNode.appendChild(comment)
                  partLoop(index + 1)
                }
              end argLoop

              beginSearchResult match
                case Searching.InsertionPoint(`beginIndex`)
                    if beginIndex % 2 == 1 =>
                  fParserConfiguration
                    .asInstanceOf[HTMLConfiguration]
                    .evaluateInputSource(
                      XMLInputSource(
                        null,
                        null,
                        null,
                        StringReader(
                          html.substring(
                            beginCharacterOffset,
                            partOffsets(beginIndex)
                          )
                        ),
                        null
                      )
                    )
                  argLoop(beginIndex)
                case Searching.Found(`beginIndex`) if beginIndex % 2 == 1 =>
                  argLoop(beginIndex)
                case Searching.Found(`beginIndex`) if beginIndex % 2 == 0 =>
                  partLoop(beginIndex)
                case _ =>
                  report.error("Unexpected text: " + text)
          finally isProcessingCharacters = false
      end characters
    parser.parse(InputSource(StringReader(html)), fragment)
    fragment

  def transformCDATASection(cdataSection: CDATASection)(using Quotes) =
    '{
      org.scalajs.dom.document.createCDATASection(${
        Expr(cdataSection.getWholeText)
      })
    }
  def transformProcessingInstruction(
      processingInstruction: ProcessingInstruction
  )(using Quotes) =
    '{
      org.scalajs.dom.document.createProcessingInstruction(
        ${
          Expr(processingInstruction.getTarget),
        },
        ${
          Expr(processingInstruction.getData),
        }
      )
    }
  def transformText(text: Text)(using Quotes) =
    '{
      org.scalajs.dom.document.createTextNode(${ Expr(text.getWholeText) })
    }
  def transformNode(node: Node)(using IndexedSeq[Expr[Any]])(using
      Expr[Nondeterminism[DefaultFuture]],
      Quotes
  ): Expr[Any] =
    import scala.quoted.quotes.reflect.*
    node match
      case element: Element =>
        transformElement(element)
      case comment: Comment =>
        transformComment(comment)
      case cdataSection: CDATASection =>
        transformCDATASection(cdataSection)
      case text: Text =>
        transformText(text)
      case processingInstruction: ProcessingInstruction =>
        transformProcessingInstruction(processingInstruction)
      case _ =>
        report.error("Unsupported node: " + node.toString)
        '{ ??? }
  def mountProperty[E <: org.scalajs.dom.Element, K, V](using Quotes)(
      element: Element,
      elementExpr: Expr[E],
      propertySymbol: scala.quoted.quotes.reflect.Symbol,
      attributeValueExpr: Expr[V],
      reifiedExpr: Expr[K]
  )(using
      Expr[Nondeterminism[DefaultFuture]],
      Type[E],
      Type[K],
      Type[V]
  ): Expr[Binding[Nothing]] =
    import scala.quoted.quotes.reflect.*
    TypeRepr.of[E].memberType(propertySymbol).asType match
      case '[propertyType] =>
        type DslType =
          Dsl.Run[K, Binding[propertyType], V]
        Implicits.search(TypeRepr.of[DslType]) match
          case success: ImplicitSearchSuccess =>
            '{
              given Nondeterminism[DefaultFuture] =
                $summon
              ${ success.tree.asExprOf[DslType] }
                .apply($reifiedExpr)
                .collect(Function.unlift { propertyValue =>
                  ${
                    Assign(
                      Select(
                        elementExpr.asTerm,
                        propertySymbol
                      ),
                      '{ propertyValue }.asTerm
                    ).asExpr
                  }
                  None
                })
            }
          case failure: ImplicitSearchFailure =>
            report.error(
              s"Cannot produce a bindable expression for the property ${propertySymbol.name}. Expect ${Type
                .show[propertyType]}, actual ${Type.show[V]}\n${failure.explanation}",
              attributeValueExpr.asTerm.pos
            )
            '{ ??? }

  def mountAttribute[E <: org.scalajs.dom.Element, K, V](
      element: Element,
      elementExpr: Expr[E],
      qName: QName,
      attributeValueExpr: Expr[V],
      reifiedExpr: Expr[K]
  )(using
      Expr[Nondeterminism[DefaultFuture]],
      Type[E],
      Type[K],
      Type[V],
      Quotes
  ): Expr[Binding[Nothing]] =
    import scala.quoted.quotes.reflect.*
    type DslType =
      Dsl.Run[K, Binding[String], V]
    Implicits.search(TypeRepr.of[DslType]) match
      case success: ImplicitSearchSuccess =>
        '{
          given Nondeterminism[DefaultFuture] = $summon
          ${ success.tree.asExprOf[DslType] }
            .apply($reifiedExpr)
            .collect(Function.unlift { stringAttributeValue =>
              ${
                qName.uri match
                  case null =>
                    '{
                      $elementExpr.setAttribute(
                        ${ Expr(qName.localpart) },
                        stringAttributeValue
                      )
                    }
                  case uri =>
                    '{
                      $elementExpr.setAttributeNS(
                        ${ Expr(uri) },
                        ${ Expr(qName.localpart) },
                        stringAttributeValue
                      )
                    }
              }
              None
            })
        }
      case failure: ImplicitSearchFailure =>
        report.error(
          s"Cannot produce a bindable string for the attribute ${qName.toString} \n${failure.explanation}",
          attributeValueExpr.asTerm.pos
        )
        '{ ??? }

  def mountElementAttributesAndChildNodes[E <: org.scalajs.dom.Element](
      element: Element,
      elementExpr: Expr[E]
  )(using argExprs: IndexedSeq[Expr[Any]])(using
      Expr[Nondeterminism[DefaultFuture]],
      Type[E],
      Quotes
  ): Expr[NodeBinding[org.scalajs.dom.Element]] =
    import scala.quoted.quotes.reflect.*
    val attributes = element.getAttributes
    val setStaticAttributeExprs =
      for i <- (0 until attributes.getLength).view yield
        val attr = attributes.item(i).asInstanceOf[Attr]
        attr.getNamespaceURI match {
          case null =>
            if !Definitions.isValidAttribute[E](
                attr.getName
              )
            then
              report.warning(
                s"${attr.getName} is not a valid attribute for <${element.getTagName}>"
              )

            '{
              $elementExpr.setAttribute(
                ${ Expr(attr.getName) },
                ${ Expr(attr.getValue) }
              )
            }
          case namespaceUri =>
            '{
              $elementExpr.setAttributeNS(
                ${ Expr(namespaceUri) },
                ${ Expr(attr.getLocalName) },
                ${ Expr(attr.getValue) }
              )
            }
        }
    val transformedChildNodes = transformNodeList(element.getChildNodes)
    val childNodesEventLoop = '{
      given Nondeterminism[DefaultFuture] = $summon
      mountChildNodes($elementExpr, $transformedChildNodes)
    }

    val transformedAttributeEventLoops =
      element.getUserData(AttributeArgumentsUserDataKey) match
        case null =>
          Nil
        case attributeBindings =>
          for
            (qName: QName, argExprs(anyAttributeValueExpr)) <- attributeBindings
              .asInstanceOf[Iterable[_]]
          yield anyAttributeValueExpr.asTerm.tpe.asType match
            case '[attributeType] =>
              val attributeValueExpr =
                anyAttributeValueExpr.asExprOf[attributeType]
              val anyReifiedExpr =
                Reset.Macros.reify[false, false, attributeType](
                  attributeValueExpr
                )
              anyReifiedExpr.asTerm.tpe.asType match
                case '[keywordType] =>
                  val reifiedExpr = anyReifiedExpr.asExprOf[keywordType]
                  qName.uri match
                    case null =>
                      val propertySymbol =
                        TypeRepr
                          .of[E]
                          .classSymbol
                          .get
                          .fieldMember(qName.localpart)
                      if propertySymbol.isValDef &&
                        propertySymbol.flags.is(Flags.Mutable)
                      then
                        mountProperty(
                          element,
                          elementExpr,
                          propertySymbol,
                          attributeValueExpr,
                          reifiedExpr
                        )
                      else
                        report.warning(
                          s"${qName.rawname} is not a valid property for <${element.getTagName}>"
                        )
                        mountAttribute(
                          element,
                          elementExpr,
                          qName,
                          attributeValueExpr,
                          reifiedExpr
                        )
                    case uri =>
                      mountAttribute(
                        element,
                        elementExpr,
                        qName,
                        attributeValueExpr,
                        reifiedExpr
                      )
    Expr.block(
      List.from(setStaticAttributeExprs),
      '{
        @inline given Nondeterminism[DefaultFuture] = $summon
        NodeBinding(
          $elementExpr,
          CovariantStreamT.mergeAll(
            ${
              Expr.ofSeq(
                Seq.from(
                  View.Appended(
                    transformedAttributeEventLoops,
                    childNodesEventLoop
                  )
                )
              )
            }
          )
        )
      }
    )

  def transformElement(element: Element)(using IndexedSeq[Expr[Any]])(using
      Expr[Nondeterminism[DefaultFuture]],
      Quotes
  ): Expr[NodeBinding[org.scalajs.dom.Element]] =
    element.getNamespaceURI match
      case null =>
        Definitions.findTypeByTagName(
          element.getTagName.toLowerCase
        ) match
          case '[elementType] =>
            '{
              val htmlElement = org.scalajs.dom.document
                .createElement(${
                  Expr(element.getTagName)
                })
                .asInstanceOf[elementType & org.scalajs.dom.Element]
              ${ mountElementAttributesAndChildNodes(element, 'htmlElement) }
            }
      case namespaceUri =>
        '{
          val elementInNamespace = org.scalajs.dom.document.createElementNS(
            ${ Expr(namespaceUri) },
            ${ Expr(element.getLocalName) }
          )
          ${ mountElementAttributesAndChildNodes(element, 'elementInNamespace) }
        }

  def transformComment(comment: Comment)(using argExprs: IndexedSeq[Expr[Any]])(
      using
      Expr[Nondeterminism[DefaultFuture]],
      Quotes
  ): Expr[Any] =
    import scala.quoted.quotes.reflect.*
    comment.getUserData(ElementArgumentUserDataKey) match
      case null =>
        '{ org.scalajs.dom.document.createComment(${ Expr(comment.getData) }) }
      case argExprs(expr) =>
        expr.asTerm.tpe.asType match
          case '[t] =>
            Reset.Macros.reify[false, false, t](expr.asExprOf[t])

  def transformNodeList(nodeList: NodeList)(using IndexedSeq[Expr[Any]])(using
      Expr[Nondeterminism[DefaultFuture]],
      Quotes
  ): Expr[BindingSeq[org.scalajs.dom.Node]] =
    import scala.quoted.quotes.reflect.report
    import scala.quoted.quotes.reflect.asTerm
    import scala.quoted.quotes.reflect.TypeRepr
    import scala.quoted.quotes.reflect.Implicits
    import scala.quoted.quotes.reflect.ImplicitSearchFailure
    import scala.quoted.quotes.reflect.ImplicitSearchSuccess
    '{
      @inline given Nondeterminism[DefaultFuture] = $summon
      Monad[BindingSeq].join(Binding.Constants(${
        Expr.ofSeq(
          (
            for i <- 0 until nodeList.getLength
            yield
              val child = nodeList.item(i)
              val transformedTerm = transformNode(child).asTerm
              transformedTerm.tpe.asType match
                case '[from] =>
                  // TODO: Use summonInline instead?
                  Implicits.search(
                    TypeRepr
                      .of[BindableSeq[from, org.scalajs.dom.Node]]
                  ) match
                    case success: ImplicitSearchSuccess =>
                      '{
                        ${
                          success.tree
                            .asExprOf[
                              BindableSeq[from, org.scalajs.dom.Node]
                            ]
                        }.apply(${
                          transformedTerm.asExprOf[from]
                        })
                      }
                    case failure: ImplicitSearchFailure =>
                      report.error(
                        s"Require a HTML DOM expression, got ${TypeRepr.of[from].show}\n${failure.explanation}",
                        transformedTerm.pos
                      )
                      '{ ??? : BindingSeq[org.scalajs.dom.Node] }
          ).toSeq
        )
      }: _*))
    }

  def consumedArgumentIndices(node: Node): BitSet =
    val nodeList = node.getChildNodes

    (0 until nodeList.getLength).view
      .flatMap(i => consumedArgumentIndices(nodeList.item(i)))
      .to(BitSet)
      ++ (node.getUserData(ElementArgumentUserDataKey) match
        case null =>
          BitSet.empty
        case arg: Int =>
          BitSet(arg)
      ) ++ (node.getUserData(AttributeArgumentsUserDataKey) match
        case null =>
          BitSet.empty
        case args: Map[_, Int @unchecked] =>
          args.values.to(BitSet)
      )

  def html(
      stringContext: Expr[StringContext],
      args: Expr[Seq[Any]]
  )(using
      Expr[Nondeterminism[DefaultFuture]],
      Quotes
  ): Expr[Any] =
    import scala.quoted.quotes.reflect.Printer
    import scala.quoted.quotes.reflect.report
    import scala.quoted.quotes.reflect.asTerm
    import scala.quoted.quotes.reflect.TypeRepr
    import scala.quoted.quotes.reflect.Flags
    import scala.quoted.quotes.reflect.Typed
    val Varargs(argExprs) = args
    val '{ StringContext($partsExpr: _*) } = stringContext
    val Expr(partList) = partsExpr
    val parts = partList.toIndexedSeq
    val fragment = parseHtmlParts(parts, argExprs)
    val isConsumed = this.consumedArgumentIndices(fragment)
    for
      (argExpr, arg) <- argExprs.view.zipWithIndex
      if !isConsumed(arg)
    do
      report.error(
        "A variable must be either an attribute value or child nodes under an element",
        argExpr.asTerm.pos
      )

    // report.info(
    //   "arg:" + fragment.getFirstChild.getChildNodes
    //     .item(1)
    //     .getUserData(ElementArgumentUserDataKey)
    // )
    // report.warning(
    //   fragment.getOwnerDocument.getImplementation
    //     .getFeature("LS", "3.0")
    //     .asInstanceOf[DOMImplementationLS]
    //     .createLSSerializer()
    //     .writeToString(fragment)
    // )
    val rootNodes = fragment.getChildNodes
    if rootNodes.getLength == 1 then
      transformNode(rootNodes.item(0))(using argExprs.toIndexedSeq)
    else transformNodeList(rootNodes)(using argExprs.toIndexedSeq)

import bindable.*
import scalaz.-\/
import scalaz.Applicative
import scalaz.DList
import scalaz.Equal
import scalaz.FingerTree
import scalaz.Free
import scalaz.IList
import scalaz.Memo
import scalaz.Monad
import scalaz.MonadPlus
import scalaz.Monoid
import scalaz.Nondeterminism
import scalaz.Reducer
import scalaz.StreamT
import scalaz.StreamT.Done
import scalaz.StreamT.Skip
import scalaz.StreamT.Yield
import scalaz.UnitReducer
import scalaz.\/-

import scala.concurrent.Future
import scala.collection.IndexedSeqView
import scalaz.Functor
import scalaz.StreamT.Step
import scala.annotation.unchecked.uncheckedVariance
import scala.util.Success
import org.scalajs.dom.Node
import org.scalajs.dom.ParentNode
import PatchStreamT.Patch
import scala.annotation.tailrec
import org.scalajs.dom.Element

def mount[A](a: A, events: Binding[A => Unit])(using
    N: Functor[DefaultFuture]
): Binding[Nothing] =
  val stream = CovariantStreamT.apply.flip(events)
  def mapEvents(
      stream: StreamT[DefaultFuture, A => Unit]
  ): StreamT[DefaultFuture, Nothing] =
    StreamT(N.map(stream.step) {
      case Yield(f, s) =>
        f(a)
        Skip(() => mapEvents(s()))
      case Skip(s) =>
        Skip(() => mapEvents(s()))
      case Done() =>
        Done()
    })
  mapEvents(stream)

def mountChildNodes(
    parent: ParentNode & Node,
    childNodes: Binding.BindingSeq[Node]
)(using N: Functor[DefaultFuture]): Binding[Nothing] =
  val patchStream =
    CovariantStreamT.apply.flip(PatchStreamT.apply.flip(childNodes))
  def mapEvents(
      patchStream: StreamT[DefaultFuture, Patch[Node]]
  ): StreamT[DefaultFuture, Nothing] =
    StreamT(N.map(patchStream.step) {
      case Yield(
            Patch.ReplaceChildren(newItems),
            s
          ) =>
        parent.replaceChildren(collection.immutable.ArraySeq.from(newItems): _*)
        Skip(() => mapEvents(s()))
      case Yield(
            Patch.Splice(
              index,
              deleteCount,
              newItems
            ),
            s
          ) =>
        val childNodes = parent.childNodes
        if (index < childNodes.length) then
          val refChild = parent.childNodes(index)
          for child <- newItems do parent.insertBefore(child, refChild)
          @tailrec
          def delete(remaining: Int, refChild: Node): Unit =
            if remaining > 0 then
              val nextSibling = refChild.nextSibling
              parent.removeChild(refChild)
              delete(remaining - 1, nextSibling)
          delete(deleteCount, refChild)
        else if (index == childNodes.length && deleteCount == 0)
          for child <- newItems do parent.appendChild(child)
        else
          throw new IllegalStateException
        Skip(() => mapEvents(s()))
      case Skip(s) =>
        Skip(() => mapEvents(s()))
      case Done() =>
        Done()
    })
  mapEvents(patchStream)

opaque type NodeBinding[+A] <: Binding[A] = Binding[A] {
  val step: DefaultFuture[
    StreamT.Yield[
      // Don't use `_ <: A` because the Scala 3 type checker could eliminate
      // wildcard types expectedly.
      // See https://github.com/lampepfl/dotty/issues/14152
      A @uncheckedVariance,
      Binding[Nothing]
    ]
  ] {
    def isCompleted: true
    def value: Some[Success[
      StreamT.Yield[
        // Don't use `_ <: A` because the Scala 3 type checker could eliminate
        // wildcard types expectedly.
        // See https://github.com/lampepfl/dotty/issues/14152
        A @uncheckedVariance,
        Binding[Nothing]
      ]
    ]]
  }
}

object NodeBinding:

  extension [A](nodeBinding: NodeBinding[A])
    def value = nodeBinding.step.value.get.get.a
    def eventLoop = nodeBinding.step.value.get.get.s()

  def apply[A](value: A, eventLoop: Binding[Nothing]): NodeBinding[A] = {
    val result: Binding[A] = CovariantStreamT(
      StreamT[DefaultFuture, A](
        Future.successful(
          StreamT.Yield(
            value,
            () => CovariantStreamT.apply[DefaultFuture, A].flip(eventLoop)
          )
        )
      )
    )
    result.asInstanceOf[NodeBinding[A]]
  }

  given [Element](using
      Applicative[DefaultFuture]
  ): BindableSeq[NodeBinding[Element], Element] = BindableSeq { nodeBinding =>
    PatchStreamT(
      CovariantStreamT(
        PatchStreamT.Patch.ReplaceChildren[Element](
          collection.View.Single(nodeBinding.value)
        ) :: CovariantStreamT.apply.flip(nodeBinding.eventLoop)
      )
    )
  }
extension (inline stringContext: StringContext)
  transparent inline def html(
      inline args: Any*
  )(using
      nondeterminism: scalaz.Nondeterminism[DefaultFuture]
  ): Any = ${
    Macros.html('stringContext, 'args)(using 'nondeterminism)
  }

def render[A](parent: ParentNode & Node, childNodes: A)(using
    bindableSeq: BindableSeq[A, Node]
)(using Monad[DefaultFuture]): Unit =
  mountChildNodes(parent, bindableSeq(childNodes)).headOption
