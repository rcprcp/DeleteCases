package com.cottagecoders.deletecases;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeleteCases {

  private DeleteCases() throws IOException {
  }

  public static void main(String... args) throws IOException {

    if (StringUtils.isEmpty(System.getenv("ZENDESK_URL"))) {
      System.out.println("Please set the ZENDESK_URL environment variable: `export ZENDESK_EMAIL=\"https://domain.zendesk.com\"`");
      System.exit(5);
    }

    if (StringUtils.isEmpty(System.getenv("ZENDESK_EMAIL"))) {
      System.out.println("Please set the ZENDESK_EMAIL environment variable: `export ZENDESK_EMAIL=\"bob@domain.com\"`");
      System.exit(5);
    }

    if (StringUtils.isEmpty(System.getenv("ZENDESK_TOKEN"))) {
      System.out.println("Please set the ZENDESK_TOKEN environment variable: `export ZENDESK_TOKEN=\"c0928blahblahblahblahg1h14g5\"`");
      System.exit(5);
    }

    // gather Zendesk info:
    ZendeskCases zd = new ZendeskCases(System.getenv("ZENDESK_URL"), System.getenv("ZENDESK_EMAIL"), System.getenv("ZENDESK_TOKEN"));
    Set<Long> zdinfo = zd.getCaseStatus();
    zd.close();

    DeleteCases dc = new DeleteCases();
    dc.process(zdinfo);
  }

  private void process(Set<Long> zdInfo) throws IOException {
    Set<String> cases = new HashSet<>();

    // gather all directories in the  current directory
    File[] files = new File("./").listFiles(File::isDirectory);
    if (files == null) {
      System.out.println("no files to process.");
      System.exit(0);
    }

    for (File file : files) {
      String[] parts = file.getName().split("_");
      if (parts.length != 4) {
        continue;
      }

      long caseNumber;
      try {
        caseNumber = Long.parseLong(parts[1]);
      } catch (NumberFormatException ex) {
        System.out.println("NumberFormatException  on '" + file.getName() + " " + ex.getMessage());
        continue;
      }

      // check ZD status here.
      if (zdInfo.contains(caseNumber)) {
        // add it to the map.
        cases.add(file.getCanonicalPath());
      }

    }
    for (String path : cases) {
      System.out.println("rm -rf " + path);
    }
    System.out.println("size " + cases.size());
  }
}