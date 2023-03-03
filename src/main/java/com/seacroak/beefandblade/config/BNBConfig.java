package com.seacroak.beefandblade.config;

public class BNBConfig {
  public Range sword_leather_range = new Range(2,3);
  public Range axe_beef_range = new Range(4,5);
  public Range sword_beef_range = new Range(0,0);
  public Range axe_leather_range = new Range(0,0);

  public static class Range {
    int min;
    int max;

    public Range(int min, int max) {
      this.min = min;
      this.max = max;
    }

    public void setMin(int min) {
      this.min = min;
    }

    public void setMax(int max) {
      this.max = max;
    }

    public int getMin() {
      return this.min;
    }

    public int getMax() {
      return this.max;
    }
  }

  public void validate() {
    sword_leather_range.setMin(Math.max(sword_leather_range.getMin(), 0));
    sword_leather_range.setMax(Math.min(sword_leather_range.getMax(), 64));

    sword_beef_range.setMin(Math.max(sword_beef_range.getMin(), 0));
    sword_beef_range.setMax(Math.min(sword_beef_range.getMax(), 64));

    axe_beef_range.setMin(Math.max(axe_beef_range.getMin(), 0));
    axe_beef_range.setMax(Math.min(axe_beef_range.getMax(), 64));

    axe_leather_range.setMin(Math.max(axe_leather_range.getMin(), 0));
    axe_leather_range.setMax(Math.min(axe_leather_range.getMax(), 64));
  }
}
