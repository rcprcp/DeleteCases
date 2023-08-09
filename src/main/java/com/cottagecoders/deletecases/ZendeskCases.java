package com.cottagecoders.deletecases;

import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Ticket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ZendeskCases {

  private Zendesk zd;

  ZendeskCases(String url, String zendeskUsername, String zendeskToken) {
    zd = new Zendesk.Builder(url)
                 .setUsername(zendeskUsername)
                 .setToken(zendeskToken)
                 .build();
  }

  Set<Long> getCaseStatus() {
    Set<Long> cases = new HashSet<>();

    for (Ticket ticket : zd.getTickets()) {
      // check all the tickets - only save SOLVED.
      if (ticket.getStatus().toString().equalsIgnoreCase("solved") || ticket.getStatus().toString().equalsIgnoreCase("closed")) {
        cases.add(ticket.getId());
      }
    }
    return cases;

  }

  void close() {
    zd.close();
  }
}

