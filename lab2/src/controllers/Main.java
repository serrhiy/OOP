package controllers;

import java.lang.reflect.InvocationTargetException;

class First {
  String a;
  String b;
  public First(String a, String b) {
    this.a = a;
    this.b = b;
  }
}

class Second extends First {
  public Second(String a) {
    super(a, "b");
  }
}

class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    final Class<? extends First> constructor = Second.class;
    final var second = constructor.getDeclaredConstructor(String.class).newInstance("a");
    System.out.println(second.b);
  }
}