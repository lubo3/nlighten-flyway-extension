package me.wildfly.flyway.extension.deployment;

/**
 * The Interface DeploymentUnitProcessor Constants.
 * 
 * @author lubo
 */
public interface DeploymentUnitProcessorConstants {

  /** The migration folder war path. */
  public static final String MIGRATION_FOLDER_WAR_PATH = "WEB-INF/classes/db/migration";

  /** The migration folder jar path. */
  public static final String MIGRATION_FOLDER_JAR_PATH = "src/main/resources/db/migration";

  /** The persistence xml war path. */
  public static final String PERSISTENCE_XML_WAR_PATH = "WEB-INF/classes/META-INF/persistence.xml";

  /** The persistence xml jar path. */
  public static final String PERSISTENCE_XML_JAR_PATH =
      "src/main/resources/META-INF/persistence.xml";

  /** The datasource element. */
  public static final String DATASOURCE_ELEMENT = "jta-data-source";

  /** The datasource regex. */
  public static final String DATASOURCE_REGEX = "</?jta-data-source>";

  /** The default datasource. */
  public static final String DEFAULT_DATASOURCE = "java:jboss/datasources/ExampleDS";

  /** The Constant jar. */
  public static final String jar = "jar";

  /** The Constant war. */
  public static final String war = "war";

  /** The Constant ear. */
  public static final String ear = "ear";
}
