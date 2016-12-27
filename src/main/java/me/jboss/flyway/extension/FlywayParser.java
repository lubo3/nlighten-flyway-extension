package me.jboss.flyway.extension;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;

/**
 * The subsystem parser, which uses stax to read and write to and from xml.
 * 
 * @author lubo
 */
class FlywayParser extends PersistentResourceXMLParser {

  /** The Constant xmlDescription. */
  private static final PersistentResourceXMLDescription xmlDescription =
      builder(FlywayRootDefinition.INSTANCE, FlywayExtension.NAMESPACE).build();

  /** {@inheritDoc} */
  @Override
  public PersistentResourceXMLDescription getParserDescription() {
    return xmlDescription;
  }

}
