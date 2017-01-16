package me.wildfly.flyway.extension;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;

/**
 * The FlywayParser class is XML description resource.
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
