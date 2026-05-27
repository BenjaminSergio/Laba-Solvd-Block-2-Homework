/**
 * JAXB-bound airline entities for Section 02 / Lecture 04.
 *
 * <p>The reference {@code fleet.xml} + {@code fleet.xsd} intentionally do
 * NOT declare an XML namespace, to keep XPath queries and the homework focused
 * on the parsing models themselves rather than {@code NamespaceContext}
 * plumbing.
 *
 * <p>For production code where the XSD declares a {@code targetNamespace},
 * add the following annotation directly above the {@code package} keyword in
 * this file:
 *
 * <pre>{@code
 * @XmlSchema(
 *     namespace = "https://solvd.com/airline/fleet/v1",
 *     elementFormDefault = XmlNsForm.QUALIFIED
 * )
 * }</pre>
 *
 * The annotation declares the namespace for every class in this package; every
 * {@code @XmlElement} and {@code @XmlRootElement} inherits it. Match the XML
 * document's {@code xmlns="..."} declaration and the XSD's
 * {@code targetNamespace="..."} exactly. Without this annotation, marshal
 * output is in the no-namespace ("") and your XSD that declares a
 * {@code targetNamespace} will reject the document.
 */
package com.solvd.airline.entity.jaxb;
