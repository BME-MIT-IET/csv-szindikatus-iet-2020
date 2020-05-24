package com.complexible.common.csv;

import com.complexible.common.csv.logger.ProcessBehaviourLogger;

import io.airlift.command.Cli;
import io.airlift.command.Help;

class Main {
    
        public static void main(String[] args) {

            if (args.length < 1) {
                ProcessBehaviourLogger.logInfo("Usage: java -jar CSV2RDF, convert <template.ttl> <input.csv> <output.ttl>");
                return;
            }

            try {  
            
                Cli.<Runnable> builder("csv2rdf").withDescription("Converts a CSV file to RDF based on a given template")
                                .withDefaultCommand(CSV2RDF.class).withCommand(CSV2RDF.class).withCommand(Help.class)
                                .build().parse(args).run();
            }
            catch (IllegalArgumentException e){
                ProcessBehaviourLogger.logError("Invalid parameters. Make sure the first file is a template with format .ttl, the input file is a .csv, the output file is a '.ttl'.");
            }
            catch (Exception e) {
                ProcessBehaviourLogger.logError(e.getMessage());
            }

        }
}