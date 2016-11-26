package me.jboss.flyway.extension;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.dmr.ModelType;

/**
 * The subsystem parser, which uses stax to read and write to and from xml.
 * 
 * @author lubo
 */
class FlywayParser extends PersistentResourceXMLParser {

  /** The Constant PATH. */
  protected static final AttributeDefinition PATH =
      new SimpleAttributeDefinitionBuilder(FlywayExtension.PATH, ModelType.STRING)
          .setAllowExpression(true).build();

  /** The Constant xmlDescription. */
  private static final PersistentResourceXMLDescription xmlDescription =
      builder(FlywayRootDefinition.INSTANCE, FlywayExtension.NAMESPACE).addAttributes(PATH).build();

  /** {@inheritDoc} */
  @Override
  public PersistentResourceXMLDescription getParserDescription() {
    return xmlDescription;
  }

}
