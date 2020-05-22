package com.complexible.common.csv;

import com.complexible.common.csv.logger.ProcessBehaviourLogger;
import com.google.common.base.Preconditions;

import io.airlift.command.Cli;
import io.airlift.command.Help;

class Main {
    
        public static void main(String[] args) {
        try { 
            if(args==null){
                throw new NullPointerException();
            }
            Preconditions.checkNotNull(args);
            Preconditions.checkNotNull(args[0]);
            Preconditions.checkNotNull(args[1]);
            Preconditions.checkNotNull(args[2]);
            Preconditions.checkArgument(args[0].endsWith(".ttl"));
            Preconditions.checkArgument(args[1].endsWith(".csv"));
            Preconditions.checkArgument(args[2].endsWith(".ttl"));           
        
            Cli.<Runnable> builder("csv2rdf").withDescription("Converts a CSV file to RDF based on a given template")
                            .withDefaultCommand(CSV2RDF.class).withCommand(CSV2RDF.class).withCommand(Help.class)
                            .build().parse(args).run();
        }
        catch (IllegalArgumentException e){
            ProcessBehaviourLogger.logError("Wrong input! Make sure the first file is a template with format .ttl, the second is the RDF-to-be .csv, the last is a .ttl\nERROR: "+(e.getMessage()));
        }
        catch (NullPointerException e){
            ProcessBehaviourLogger.logError("Wrong input! Make sure to give 3 files in input\nERROR: " + (e.getMessage()));
        }
        catch (Exception e) {
            ProcessBehaviourLogger.logError("ERROR: "+(e.getMessage())); }
        }
}
