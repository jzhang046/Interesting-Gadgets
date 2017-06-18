//This is the solution for task 2

import java.util.*;

public class Solution {
  public static void main(String[] args) {
    String input = new StringBuilder()
                    .append("Mon 01:00-23:00\n")
                    .append("Tue 01:00-23:00\n")
                    .append("Wed 01:00-23:00\n")
                    .append("Thu 01:00-23:00\n")
                    .append("Fri 01:00-13:00\n")
                    .append("Sat 01:00-23:00\n")
                    .append("Sun 01:00-21:00")
                    .toString();

    System.out.println(solution(input));
  }

  public static int solution(String S) {
    TreeSet<TimeIntvInWeek> occupiedTime = parseTimeIntv(S);
    occupiedTime.add(new TimeIntvInWeek("Mon 00:00-00:00"));
    occupiedTime.add(new TimeIntvInWeek("Sun 24:00-24:00"));

    Iterator itor = occupiedTime.iterator();

    TreeSet<TimeIntvInWeek> emptyTime = new TreeSet<TimeIntvInWeek>(new TimeLengthComparator());
    TimeIntvInWeek prev;

    if (itor.hasNext()) {
      prev = (TimeIntvInWeek) itor.next();
    
      while (itor.hasNext()) {
        TimeIntvInWeek current = (TimeIntvInWeek) itor.next();

        //debug
        System.out.println(prev);
        System.out.println(current);

        emptyTime.add(TimeIntvInWeek.minus(prev, current));
        prev = current;
      }

      //debug
      System.out.println(emptyTime.last().getStartMins());
      System.out.println(emptyTime.last().getEndMins());
      System.out.println("======");

      return emptyTime.last().getLength();
    } else {
      return 0;
    }
  }

  private static TreeSet<TimeIntvInWeek> parseTimeIntv(String S) {
    TreeSet<TimeIntvInWeek> rtn = new TreeSet<TimeIntvInWeek>(new StartTimeComparator());

    Scanner s = new Scanner(S);
    while (s.hasNextLine()) {
      rtn.add(new TimeIntvInWeek(s.nextLine()));
    }

    return rtn;
  }
}

class TimeLengthComparator implements Comparator<TimeIntvInWeek> {
  @Override
  public int compare(TimeIntvInWeek t1, TimeIntvInWeek t2) {
    int length1 = t1.getLength();
    int length2 = t2.getLength();
    if (length1 > length2) {
      return 1;
    } else if (length1 < length2) {
      return -1;
    } else {
      return 0;
    }
  }
}

class StartTimeComparator implements Comparator<TimeIntvInWeek> {
  @Override
  public int compare(TimeIntvInWeek t1, TimeIntvInWeek t2) {
    int start1 = t1.getStartMins();
    int start2 = t2.getStartMins();
    if (start1 > start2) {
      return 1;
    } else if (start1 < start2) {
      return -1;
    } else {
      return 0;
    }
  }
} 

class TimeIntvInWeek {

  private int startMins;
  private int endMins; 

  public TimeIntvInWeek(String S) {
    int day = this.parseDay(S);   

    this.startMins = this.calcMins(day, this.parseStartHour(S), this.parseStartMin(S));
    this.endMins = this.calcMins(day, this.parseEndHour(S), this.parseEndMin(S));    
  }

  public TimeIntvInWeek(int strt, int nd) {
    this.startMins = strt;
    this.endMins = nd;
  }

  public static TimeIntvInWeek minus(TimeIntvInWeek prev, TimeIntvInWeek current) {
    return new TimeIntvInWeek(prev.getEndMins(), current.getStartMins());
  }

  public int getLength() {
    return this.endMins - this.startMins;
  }

  public int getStartMins() {
    return this.startMins;
  }

  public int getEndMins() {
    return this.endMins;
  }

  private int parseDay(String S) {

    String dayString = S.substring(0, 3);

    if (dayString.equals("Mon")) {
      return 1;
    } else if (dayString.equals("Tue")) {
      return 2;
    } else if (dayString.equals("Wed")) {
      return 3;
    } else if (dayString.equals("Thu")) {
      return 4;
    } else if (dayString.equals("Fri")) {
      return 5;
    } else if (dayString.equals("Sat")) {
      return 6;
    }
    else {
      return 7;
    }
  }

  private int parseStartHour(String S) {
    return this.parseInt(S, 4, 6);
  }

  private int parseEndHour(String S) {
    return this.parseInt(S, 10, 12); 
  }

  private int parseStartMin(String S) {
    return this.parseInt(S, 7, 9);
  }

  private int parseEndMin(String S) {
    return this.parseInt(S, 13, 15);
  }

  private int parseInt(String S, int startp, int endp) {
    Scanner s = new Scanner(S.substring(startp, endp));
    //debug
    int rtn = s.nextInt();
    System.out.println(rtn);
    return rtn;
    //
    //return s.nextInt();
  }

  private int calcMins(int day, int hour, int min) {
    return (int)(day - 1) * 24 * 60 + (int)hour * 60 + min;
  }

  public String toString() {
    return "" + this.startMins + "-" + this.endMins;
  }
}
