package com.cottagecoders.deletecases;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Status;
import org.zendesk.client.v2.model.Ticket;

import java.io.File;
import java.io.IOException;

public class DeleteCases {

  @Parameter(names = {"-s", "--separator"}, description = "separator for fields in directory name (default is _)")
  public String separator = "_";

  @Parameter(names = {"-t", "--token"}, description = "token number to check (2 for IBM_12345_10_31)")
  public int tokenNumber = 2;


  private DeleteCases() throws IOException {
  }

  public static void main(String... args) throws IOException {

    if (StringUtils.isEmpty(System.getenv("ZENDESK_URL"))) {
      System.out.println(
              "Please set the ZENDESK_URL environment variable: `export ZENDESK_EMAIL=\"https://domain.zendesk.com\"`");
      System.exit(5);
    }

    if (StringUtils.isEmpty(System.getenv("ZENDESK_EMAIL"))) {
      System.out.println("Please set the ZENDESK_EMAIL environment variable: `export ZENDESK_EMAIL=\"bob@domain" +
                                 ".com\"`");
      System.exit(5);
    }

    if (StringUtils.isEmpty(System.getenv("ZENDESK_TOKEN"))) {
      System.out.println("Please set the ZENDESK_TOKEN environment variable: `export " + "ZENDESK_TOKEN" +
                                 "=\"c0928blahblahblahblahg1h14g5\"`");
      System.exit(5);
    }

    try (Zendesk zd =
                 new Zendesk.Builder(System.getenv("ZENDESK_URL")).setUsername(System.getenv("ZENDESK_EMAIL")).setToken(
            System.getenv("ZENDESK_TOKEN")).build()) {

      DeleteCases dc = new DeleteCases();
      JCommander.newBuilder().addObject(dc).build().parse(args);
      dc.process(zd);
    }
  }

  private void process(Zendesk zd) throws IOException {

    // gather all directories in the  current directory
    File[] files = new File("./").listFiles(File::isDirectory);
    if (files == null) {
      System.out.println("no files to process.");
      System.exit(0);
    }

    for (File file : files) {
      String[] parts = file.getName().split(separator);

      try {
        if (parts.length > tokenNumber - 1) {
          Ticket t = zd.getTicket(Long.parseLong(parts[tokenNumber - 1]));
          if (t != null) {
            if (t.getStatus().equals(Status.CLOSED) || t.getStatus().equals(Status.SOLVED)) {
              System.out.println("rm -rf " + file.getCanonicalPath());
            }
          }
        }
      } catch (NumberFormatException ex) {
        //System.out.println("NumberFormatException  on '" + file.getName() + " " + ex.getMessage());
        continue;
      }
    }
  }
}