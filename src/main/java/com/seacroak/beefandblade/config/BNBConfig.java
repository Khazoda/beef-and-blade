package com.seacroak.beefandblade.config;

public class BNBConfig {
  public Range sword_leather_range = new Range();
  public Range sword_beef_range = new Range();
  public Range axe_beef_range = new Range();
  public Range axe_leather_range = new Range();

  public static class Range {
    public int min = 0;
    public int max = 1;
  }

  public void validate() {
    sword_leather_range.min = Math.min(sword_leather_range.min, 0);
    sword_leather_range.max = Math.max(sword_leather_range.max, 64);

    sword_beef_range.min = Math.min(sword_beef_range.min, 0);
    sword_beef_range.max = Math.max(sword_beef_range.max, 64);

    axe_beef_range.min = Math.min(axe_beef_range.min, 0);
    axe_beef_range.max = Math.max(axe_beef_range.max, 64);

    axe_leather_range.min = Math.min(axe_leather_range.min, 0);
    axe_leather_range.max = Math.max(axe_leather_range.max, 64);
  }
}
