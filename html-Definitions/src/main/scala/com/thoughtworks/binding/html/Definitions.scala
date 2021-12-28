// Don't edit this file, because it is generated by `sbt generateHtmlDefinitions`
package com.thoughtworks.binding.html
import org.scalajs.dom.*
import scala.quoted.*
import ScalaJsDomMissingTypes.*
object Definitions {
  def findTypeByTagName(
      tagName: String
  )(implicit quotes: Quotes): Type[_ <: HTMLElement] = {
    (tagName: @scala.annotation.switch) match {
      case "tbody" =>
        Type.of[HTMLTableSectionElement]
      case "del" =>
        Type.of[HTMLModElement]
      case "pre" =>
        Type.of[HTMLPreElement]
      case "bdo" =>
        Type.of[HTMLElement]
      case "textarea" =>
        Type.of[HTMLTextAreaElement]
      case "thead" =>
        Type.of[HTMLTableSectionElement]
      case "figcaption" =>
        Type.of[HTMLElement]
      case "s" =>
        Type.of[HTMLElement]
      case "ol" =>
        Type.of[HTMLOListElement]
      case "span" =>
        Type.of[HTMLSpanElement]
      case "body" =>
        Type.of[HTMLBodyElement]
      case "area" =>
        Type.of[HTMLAreaElement]
      case "menu" =>
        Type.of[HTMLMenuElement]
      case "hr" =>
        Type.of[HTMLHRElement]
      case "h4" =>
        Type.of[HTMLHeadingElement]
      case "source" =>
        Type.of[HTMLSourceElement]
      case "h3" =>
        Type.of[HTMLHeadingElement]
      case "dt" =>
        Type.of[HTMLElement]
      case "map" =>
        Type.of[HTMLMapElement]
      case "nav" =>
        Type.of[HTMLElement]
      case "abbr" =>
        Type.of[HTMLElement]
      case "samp" =>
        Type.of[HTMLElement]
      case "li" =>
        Type.of[HTMLLIElement]
      case "style" =>
        Type.of[HTMLStyleElement]
      case "param" =>
        Type.of[HTMLParamElement]
      case "meter" =>
        Type.of[HTMLMeterElement]
      case "br" =>
        Type.of[HTMLBRElement]
      case "hgroup" =>
        Type.of[HTMLElement]
      case "head" =>
        Type.of[HTMLHeadElement]
      case "u" =>
        Type.of[HTMLElement]
      case "dfn" =>
        Type.of[HTMLElement]
      case "img" =>
        Type.of[HTMLImageElement]
      case "data" =>
        Type.of[HTMLDataElement]
      case "optgroup" =>
        Type.of[HTMLOptGroupElement]
      case "audio" =>
        Type.of[HTMLAudioElement]
      case "ul" =>
        Type.of[HTMLUListElement]
      case "track" =>
        Type.of[HTMLTrackElement]
      case "label" =>
        Type.of[HTMLLabelElement]
      case "option" =>
        Type.of[HTMLOptionElement]
      case "a" =>
        Type.of[HTMLAnchorElement]
      case "div" =>
        Type.of[HTMLDivElement]
      case "rt" =>
        Type.of[HTMLElement]
      case "article" =>
        Type.of[HTMLElement]
      case "strong" =>
        Type.of[HTMLElement]
      case "wbr" =>
        Type.of[HTMLElement]
      case "main" =>
        Type.of[HTMLElement]
      case "embed" =>
        Type.of[HTMLEmbedElement]
      case "legend" =>
        Type.of[HTMLLegendElement]
      case "section" =>
        Type.of[HTMLElement]
      case "i" =>
        Type.of[HTMLElement]
      case "object" =>
        Type.of[HTMLObjectElement]
      case "fieldset" =>
        Type.of[HTMLFieldSetElement]
      case "base" =>
        Type.of[HTMLBaseElement]
      case "code" =>
        Type.of[HTMLElement]
      case "ins" =>
        Type.of[HTMLModElement]
      case "h2" =>
        Type.of[HTMLHeadingElement]
      case "td" =>
        Type.of[HTMLTableCellElement]
      case "datalist" =>
        Type.of[HTMLDataListElement]
      case "select" =>
        Type.of[HTMLSelectElement]
      case "small" =>
        Type.of[HTMLElement]
      case "aside" =>
        Type.of[HTMLElement]
      case "em" =>
        Type.of[HTMLElement]
      case "button" =>
        Type.of[HTMLButtonElement]
      case "rp" =>
        Type.of[HTMLElement]
      case "dl" =>
        Type.of[HTMLDListElement]
      case "th" =>
        Type.of[HTMLTableCellElement]
      case "tfoot" =>
        Type.of[HTMLTableSectionElement]
      case "meta" =>
        Type.of[HTMLMetaElement]
      case "q" =>
        Type.of[HTMLQuoteElement]
      case "tr" =>
        Type.of[HTMLTableRowElement]
      case "b" =>
        Type.of[HTMLElement]
      case "cite" =>
        Type.of[HTMLElement]
      case "blockquote" =>
        Type.of[HTMLQuoteElement]
      case "progress" =>
        Type.of[HTMLProgressElement]
      case "col" =>
        Type.of[HTMLTableColElement]
      case "video" =>
        Type.of[HTMLVideoElement]
      case "caption" =>
        Type.of[HTMLTableCaptionElement]
      case "noscript" =>
        Type.of[HTMLElement]
      case "link" =>
        Type.of[HTMLLinkElement]
      case "p" =>
        Type.of[HTMLParagraphElement]
      case "h6" =>
        Type.of[HTMLHeadingElement]
      case "iframe" =>
        Type.of[HTMLIFrameElement]
      case "h1" =>
        Type.of[HTMLHeadingElement]
      case "address" =>
        Type.of[HTMLElement]
      case "details" =>
        Type.of[HTMLDetailsElement]
      case "picture" =>
        Type.of[HTMLPictureElement]
      case "header" =>
        Type.of[HTMLElement]
      case "template" =>
        Type.of[HTMLTemplateElement]
      case "sub" =>
        Type.of[HTMLElement]
      case "time" =>
        Type.of[HTMLTimeElement]
      case "script" =>
        Type.of[HTMLScriptElement]
      case "title" =>
        Type.of[HTMLTitleElement]
      case "output" =>
        Type.of[HTMLOutputElement]
      case "figure" =>
        Type.of[HTMLElement]
      case "form" =>
        Type.of[HTMLFormElement]
      case "html" =>
        Type.of[HTMLHtmlElement]
      case "ruby" =>
        Type.of[HTMLElement]
      case "mark" =>
        Type.of[HTMLElement]
      case "bdi" =>
        Type.of[HTMLElement]
      case "kbd" =>
        Type.of[HTMLElement]
      case "slot" =>
        Type.of[HTMLSlotElement]
      case "sup" =>
        Type.of[HTMLElement]
      case "h5" =>
        Type.of[HTMLHeadingElement]
      case "footer" =>
        Type.of[HTMLElement]
      case "input" =>
        Type.of[HTMLInputElement]
      case "summary" =>
        Type.of[HTMLElement]
      case "dialog" =>
        Type.of[HTMLDialogElement]
      case "var" =>
        Type.of[HTMLElement]
      case "canvas" =>
        Type.of[HTMLCanvasElement]
      case "table" =>
        Type.of[HTMLTableElement]
      case "colgroup" =>
        Type.of[HTMLTableColElement]
      case "dd" =>
        Type.of[HTMLElement]
      case _ =>
        Type.of[HTMLUnknownElement]
    }
  }
  def isValidAttribute[E](
      attributeName: String
  )(implicit elementType: Type[E], quotes: Quotes): Boolean = {
    import quotes.reflect.TypeRepr
    (attributeName: @scala.annotation.switch) match {
      case "srcdoc" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLIFrameElement])
      case "crossorigin" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLAudioElement | HTMLImageElement | HTMLLinkElement |
            HTMLScriptElement | HTMLVideoElement
        ])
      case "onmessage" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "ondurationchange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "allowfullscreen" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLIFrameElement])
      case "onrejectionhandled" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "defer" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLScriptElement])
      case "for" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLLabelElement | HTMLOutputElement])
      case "onblur" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "coords" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAreaElement])
      case "minlength" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLTextAreaElement])
      case "span" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTableColElement])
      case "method" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLFormElement])
      case "ondragleave" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onmouseover" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onmousemove" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "name" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLButtonElement | HTMLFieldSetElement | HTMLInputElement |
            HTMLOutputElement | HTMLSelectElement | HTMLTextAreaElement |
            HTMLFormElement | HTMLIFrameElement | HTMLObjectElement |
            HTMLMapElement | HTMLMetaElement | HTMLParamElement |
            HTMLSlotElement
        ])
      case "formtarget" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLButtonElement | HTMLInputElement])
      case "onplaying" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "is" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onloadeddata" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "accept" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement])
      case "loop" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAudioElement | HTMLVideoElement])
      case "onvolumechange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "rowspan" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTableCellElement])
      case "step" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement])
      case "onbeforeprint" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "ondragstart" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "ondragover" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "size" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLSelectElement])
      case "optimum" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLMeterElement])
      case "dirname" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLTextAreaElement])
      case "pattern" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement])
      case "oncontextmenu" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "nonce" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "ondrop" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onpause" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "oncopy" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onwheel" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onafterprint" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "abbr" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTableCellElement])
      case "onsubmit" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "checked" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement])
      case "style" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "usemap" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLImageElement])
      case "onmouseup" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "scope" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTableCellElement])
      case "oncanplaythrough" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onfocus" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onreset" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onload" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "enterkeyhint" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onsecuritypolicyviolation" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "ondblclick" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onselect" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "data" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLObjectElement])
      case "onslotchange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onratechange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "datetime" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLModElement | HTMLTimeElement])
      case "label" =>
        TypeRepr.of[E] <:< (TypeRepr
          .of[HTMLOptGroupElement | HTMLOptionElement | HTMLTrackElement])
      case "hidden" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onkeypress" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "rel" =>
        TypeRepr.of[E] <:< (TypeRepr
          .of[HTMLAnchorElement | HTMLAreaElement | HTMLLinkElement])
      case "srclang" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTrackElement])
      case "loading" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLImageElement | HTMLIFrameElement])
      case "ononline" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "ondrag" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "height" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLCanvasElement | HTMLEmbedElement | HTMLIFrameElement |
            HTMLImageElement | HTMLInputElement | HTMLObjectElement |
            HTMLSourceElement | HTMLPictureElement | HTMLVideoElement
        ])
      case "onwaiting" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "nomodule" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLScriptElement])
      case "onseeking" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "as" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLLinkElement])
      case "autoplay" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAudioElement | HTMLVideoElement])
      case "min" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLMeterElement])
      case "onmessageerror" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "ontoggle" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "accesskey" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "selected" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLOptionElement])
      case "oninvalid" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onmouseleave" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onsuspend" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "readonly" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLTextAreaElement])
      case "contenteditable" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "color" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLLinkElement])
      case "onemptied" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "placeholder" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLTextAreaElement])
      case "onresize" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "http-equiv" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLMetaElement])
      case "open" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLDetailsElement | HTMLDialogElement])
      case "autocapitalize" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "formmethod" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLButtonElement | HTMLInputElement])
      case "srcset" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLImageElement | HTMLSourceElement])
      case "onstalled" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "playsinline" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLVideoElement])
      case "onplay" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "oncancel" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "itemref" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "oncontextrestored" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "ping" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAnchorElement | HTMLAreaElement])
      case "poster" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLVideoElement])
      case "onended" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "imagesrcset" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLLinkElement])
      case "onprogress" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "dir" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onchange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "low" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLMeterElement])
      case "onpaste" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "id" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "async" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLScriptElement])
      case "onmousedown" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "itemprop" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "wrap" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTextAreaElement])
      case "formnovalidate" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLButtonElement | HTMLInputElement])
      case "max" =>
        TypeRepr.of[E] <:< (TypeRepr
          .of[HTMLInputElement | HTMLMeterElement | HTMLProgressElement])
      case "onkeydown" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "itemid" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "cite" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLQuoteElement | HTMLModElement])
      case "draggable" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "autofocus" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "alt" =>
        TypeRepr.of[E] <:< (TypeRepr
          .of[HTMLAreaElement | HTMLImageElement | HTMLInputElement])
      case "onmouseout" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "allow" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLIFrameElement])
      case "onhashchange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "onloadedmetadata" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "charset" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLMetaElement])
      case "default" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTrackElement])
      case "oncanplay" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onbeforeunload" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "ismap" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLImageElement])
      case "sandbox" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLIFrameElement])
      case "src" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLAudioElement | HTMLEmbedElement | HTMLIFrameElement |
            HTMLImageElement | HTMLInputElement | HTMLScriptElement |
            HTMLSourceElement | HTMLVideoElement | HTMLTrackElement
        ])
      case "onloadstart" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "translate" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onclick" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onpageshow" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "novalidate" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLFormElement])
      case "colspan" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTableCellElement])
      case "onscroll" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "content" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLMetaElement])
      case "media" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLLinkElement | HTMLMetaElement | HTMLSourceElement |
            HTMLPictureElement | HTMLStyleElement
        ])
      case "itemscope" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onpagehide" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "accept-charset" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLFormElement])
      case "onmouseenter" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onunload" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "onformdata" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onauxclick" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "class" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "disabled" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLButtonElement | HTMLInputElement | HTMLOptGroupElement |
            HTMLOptionElement | HTMLSelectElement | HTMLTextAreaElement |
            HTMLFieldSetElement | HTMLLinkElement
        ])
      case "target" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLAnchorElement | HTMLAreaElement | HTMLBaseElement |
            HTMLFormElement
        ])
      case "shape" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAreaElement])
      case "oncontextlost" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "sizes" =>
        TypeRepr.of[E] <:< (TypeRepr
          .of[HTMLLinkElement | HTMLImageElement | HTMLSourceElement])
      case "enctype" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLFormElement])
      case "multiple" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLSelectElement])
      case "kind" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTrackElement])
      case "title" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLElement | HTMLInputElement | HTMLLinkElement | HTMLStyleElement
        ])
      case "lang" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "formaction" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLButtonElement | HTMLInputElement])
      case "headers" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTableCellElement])
      case "itemtype" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "type" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLAnchorElement | HTMLLinkElement | HTMLButtonElement |
            HTMLEmbedElement | HTMLObjectElement | HTMLSourceElement |
            HTMLInputElement | HTMLOListElement | HTMLScriptElement
        ])
      case "form" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLButtonElement | HTMLFieldSetElement | HTMLInputElement |
            HTMLObjectElement | HTMLOutputElement | HTMLSelectElement |
            HTMLTextAreaElement
        ])
      case "formenctype" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLButtonElement | HTMLInputElement])
      case "required" =>
        TypeRepr.of[E] <:< (TypeRepr
          .of[HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement])
      case "href" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLAnchorElement | HTMLAreaElement | HTMLLinkElement |
            HTMLBaseElement
        ])
      case "imagesizes" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLLinkElement])
      case "onunhandledrejection" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "start" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLOListElement])
      case "maxlength" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement | HTMLTextAreaElement])
      case "ontimeupdate" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onlanguagechange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "hreflang" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAnchorElement | HTMLLinkElement])
      case "slot" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "preload" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAudioElement | HTMLVideoElement])
      case "tabindex" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "rows" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTextAreaElement])
      case "ondragenter" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "width" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLCanvasElement | HTMLEmbedElement | HTMLIFrameElement |
            HTMLImageElement | HTMLInputElement | HTMLObjectElement |
            HTMLSourceElement | HTMLPictureElement | HTMLVideoElement
        ])
      case "autocomplete" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLFormElement | HTMLInputElement | HTMLSelectElement |
            HTMLTextAreaElement
        ])
      case "inputmode" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onstorage" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "reversed" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLOListElement])
      case "onkeyup" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onclose" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "download" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAnchorElement | HTMLAreaElement])
      case "value" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLButtonElement | HTMLOptionElement | HTMLDataElement |
            HTMLInputElement | HTMLLIElement | HTMLMeterElement |
            HTMLProgressElement | HTMLParamElement
        ])
      case "decoding" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLImageElement])
      case "onerror" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "muted" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAudioElement | HTMLVideoElement])
      case "onseeked" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "action" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLFormElement])
      case "onoffline" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "ondragend" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "referrerpolicy" =>
        TypeRepr.of[E] <:< (TypeRepr.of[
          HTMLAnchorElement | HTMLAreaElement | HTMLIFrameElement |
            HTMLImageElement | HTMLLinkElement | HTMLScriptElement
        ])
      case "integrity" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLLinkElement | HTMLScriptElement])
      case "oncuechange" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "oninput" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "high" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLMeterElement])
      case "cols" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLTextAreaElement])
      case "list" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLInputElement])
      case "spellcheck" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "onpopstate" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLBodyElement])
      case "oncut" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLElement])
      case "controls" =>
        TypeRepr.of[E] <:< (TypeRepr.of[HTMLAudioElement | HTMLVideoElement])
      case _ =>
        false
    }
  }
}
