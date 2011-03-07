//package com.gamefreezer.galaga;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//
//import java.util.List;
//import java.util.Properties;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class FormationTest {
//
//    private Properties properties;
//    private String path = "data/test/formation01.properties";
//    private Formation formation;
//    private List<Alien> listOfAliens;
//
//    @Before
//    public void setUp() {
//	formation = new Formation(path);
//	properties = formation.getProperties();
//	listOfAliens = formation.createAliens();
//    }
//
//    @Test
//    public void canGetFormationsListFromFormationsFactory() {
//	List<Formation> formations = FormationsFactory
//		.createFormations("data/test/");
//	assertThat("all formations read", formations.size(), is(1));
//    }
//
//    @Test
//    public void getExpectedNumberOfAliensInListReadFromFile() {
//	assertThat("all aliens created", listOfAliens.size(), is(43));
//    }
//
//    @Test
//    public void canReadAPropertiesFile() {
//	assertThat("read properties ok", properties.containsKey("aProperty"),
//		is(true));
//    }
//
//    @Test
//    public void canParseAnIntFromProperties() {
//	assertThat("int parsed ok", Integer.parseInt(properties
//		.getProperty("number")), is(76));
//    }
//
//    @Test
//    public void retrievingAMissingValueReturnsNull() {
//	assertEquals("missing value returns null", properties
//		.getProperty("not_there"), null);
//    }
//
//    @Test(expected = NumberFormatException.class)
//    public void parsingAMissingValueThrowsARuntimeError() {
//	Integer.parseInt(properties.getProperty("not_there"));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void checkForIllegalArgumentExceptionOnInvalidPath() {
//	formation = new Formation("this_path_does_not_exist");
//    }
//
//}
