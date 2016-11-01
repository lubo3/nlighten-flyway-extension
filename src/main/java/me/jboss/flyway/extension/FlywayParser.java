package me.jboss.flyway.extension;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;

/**
 * The subsystem parser, which uses stax to read and write to and from xml
 */
class FlywayParser extends PersistentResourceXMLParser {

  private static final PersistentResourceXMLDescription xmlDescription =
      builder(FlywayRootDefinition.INSTANCE, FlywayExtension.NAMESPACE)
          .addChild(builder(DataSourceNameDefinition.INSTANCE).setXmlElementName("deployment-type")
              .addAttributes(DataSourceNameDefinition.PATH))
          .build();

  @Override
  public PersistentResourceXMLDescription getParserDescription() {
    return xmlDescription;
  }

}
