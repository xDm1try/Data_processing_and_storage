package ru.nsu.fit.muraviev.java.xml_1;

import org.xml.sax.SAXException;
import ru.nsu.fit.muraviev.generated.*;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Application {
  private static Map<String, PersonData> parsedData = new HashMap<>();
  private static final Map<String, PersonType> collectedData = new HashMap<>();
  public static void main(String[] args) {
    try (InputStream inputStream = new FileInputStream("people.xml")) {
       Application.parsedData = new PersonParser().parse(inputStream);
    } catch (IOException | XMLStreamException e) {
      e.printStackTrace();
    }

    People people = new People();
    for (var info : Application.parsedData.values()) {
      PersonType person = new PersonType();
      setPersonInfo(person, info);
      collectedData.put(info.id, person);
      parsedData.put(info.id, info);
    }

    for (var person : collectedData.values()) {
      setSpouse(person);
      setChildren(person);
      setParents(person);
      setSiblings(person);
    }

    people.getPerson().addAll(collectedData.values());

    try {
      JAXBContext jc = null;
      ClassLoader classLoader = People.class.getClassLoader();
      jc = JAXBContext.newInstance("ru.nsu.fit.muraviev.generated", classLoader);
      Marshaller writer = jc.createMarshaller();

      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      File schemaFile = new File("schema.xsd");
      writer.setSchema(schemaFactory.newSchema(schemaFile));
      writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      writer.marshal(people, new File("result.xml"));
    } catch (JAXBException | SAXException e) {
      e.printStackTrace();
    }


  }

  private static void setPersonInfo(PersonType person, PersonData info) {
    person.setId(info.id);
    person.setName(info.getFullName());
    person.setGender(GenderType.fromValue(info.gender));
  }

  private static void setSpouse(PersonType person) {
    var info = parsedData.get(person.getId());

    if (info.wifeId != null) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(info.wifeId));
      person.setWife(personRef);

    } else if (info.husbandId != null) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(info.husbandId));
      person.setHusband(personRef);
    }
  }

  private static void setChildren(PersonType person) {
    var info = parsedData.get(person.getId());
    ChildrenType childrenType = new ChildrenType();

    for (var daughterId : info.daughtersId) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(daughterId));
      childrenType.getDaughter().add(personRef);
    }
    for (var sonId : info.sonsId) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(sonId));
      childrenType.getSon().add(personRef);
    }
    person.getChildren().add(childrenType);
  }

  private static void setParents(PersonType person) {
    PersonData info = parsedData.get(person.getId());
    ParentsType parentsType = new ParentsType();

    if (info.motherId != null) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(info.motherId));
      parentsType.setMother(personRef);
    }
    if (info.fatherId != null) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(info.fatherId));
      parentsType.setFather(personRef);
    }

    person.getParents().add(parentsType);
  }

  private static void setSiblings(PersonType person) {
    var info = parsedData.get(person.getId());
    SiblingsType siblingsType = new SiblingsType();

    for (var sisterId : info.sistersId) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(sisterId));
      siblingsType.getSister().add(personRef);
    }
    for (var brotherId : info.brothersId) {
      PersonRef personRef = new PersonRef();
      personRef.setId(collectedData.get(brotherId));
      siblingsType.getBrother().add(personRef);
    }
    person.getSiblings().add(siblingsType);
  }
}
