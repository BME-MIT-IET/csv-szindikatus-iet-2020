Feature: CSV2RDF Game

  Scenario: Empty file
    Given Have a template: "templateEmpty", CSV file: "carsEmpty" and output file: "cars"
    And Create CSVRDF instance
    When Running the conversion, file was empty
    Then Precondition not fulfilled

  Scenario: Convert file into RDF
    Given Have a template: "template", CSV file: "cars" and output file: "cars"
    And Create CSVRDF instance
    When All Precondition fulfilled
    Then CSV is converted into RDF

  Scenario: BNode generation successfull
    Given Create BNode instance
    When Generate BNode with rowIndex 3 and string row
    Then Check if generation was successfull

  Scenario: File was not found
    Given Have a template: "templateNonExisting", CSV file: "carsNonExisting" and output file: "cars"
    When Running the conversion, file was not found
    Then Precondition not fulfilled


  Scenario: Result of Conversion is correct
    Given Have a template: "template", CSV file: "cars" and output file: "cars"
    Given Run the conversion
    When Result is equal to the expected
    Then CSV is converted into RDF

