Feature: CSV2RDF Game

   Scenario: Empty file
     Given Have a template: "example\\cars\\template.ttl", CSV file: "example\\cars\\cars.csv" and output file: "example\\cars\\cars.ttl"
     And Create CSVRDF instance
     When Empty file
     Then Precondition not fulfilled

    Scenario: Convert file into RDF
      Given Have a template: "example\\cars\\template.ttl", CSV file: "example\\cars\\cars.csv" and output file: "example\\cars\\cars.ttl"
      And Create CSVRDF instance
      When All Precondition fulfilled
      Then CSV is converted into RDF

   Scenario: BNode generation successfull
     Given Create BNode instance
     When Generate BNode with rowIndex 3 and string row
     Then Check if generation was successfull

  Scenario: TemplateLiteral generation successfull
    Given Create TemplateLiteral instance
    When Generate TemplateLiteral with rowIndex 3 and string row
    Then Check if generation was successfull

