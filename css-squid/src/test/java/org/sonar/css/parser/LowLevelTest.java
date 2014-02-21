/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class LowLevelTest extends TestBase{

  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void strings() {
    assertThat(b.rule(CssGrammar.string))
        .matches("\"\"")
        .matches("\"subs.css\"")
        .matches("'asdawddawd'")
        .matches("''")
        .notMatches("'")
        .notMatches("\"")
        .notMatches("'\"")
        .matches("\"asdasd\\\n sdfsdfsdf \\\n\"")
        .matches("\"this is a 'string'\"")
        .matches("\"this is a \\\"string\\\"\"")
        .matches("'this is a \"string\"'")
        .matches("'this is a \\'string\\''");



  }

  @Test
  public void idents() {
    assertThat(b.rule(CssGrammar.ident))
        .matches("p")
        .matches("b\\&W\\?")
        .matches("B\\&W\\?")
        .matches("-moz-box-sizing")
        .matches("_dunno-what");
  }

  @Test
  public void declaration() {
    assertThat(b.rule(CssGrammar.declaration))
        .matches("color: blue")
        .notMatches("color: blue;")
        .matches("*width: 200px");
  }

  @Test
  public void numbers(){
    assertThat(b.rule(CssGrammar.number))
    .matches("0.5")
    .matches("1")
    .matches("-3")
    .matches("-12.23")
    .notMatches("3.12em")
    .notMatches("0.2%")
    ;
  }

  @Test
  public void percentage(){
    assertThat(b.rule(CssGrammar.percentage))
    .matches("0.5%")
    .matches("1%")
    .matches("-3%")
    .matches("-12.23%")
    .notMatches("12.4")
    ;
  }

  @Test
  public void dimension(){
    assertThat(b.rule(CssGrammar.dimension))
    .matches("0.5em")
    .matches("1ex")
    .matches("-3px")
    .notMatches("-12.23wookie")
    .notMatches("12.4")
    ;
  }

  @Test
  public void selector() {
    assertThat(b.rule(CssGrammar.selector))
        .matches("h6")
        .matches("h1, h2")
        .matches("h4 + h5")
        .matches("h3, h4 + h5")
        .notMatches("h3{")
        .matches(code("p[example=\"public class foo\\",
          "{\\",
          "    private int x;\\",
          "\\",
          "    foo(int x) {\\",
          "        this.x = x;\\",
          "    }\\",
          "\\",
          "}\"]"))
          .matches("h1,\nh2")
          .matches("h2\n,h3")
          .matches("h2 /* comment*/")
          .matches("h2\n /* comment*/")
          .matches("* ");

  }

  @Test
  public void uri() {
    assertThat(b.rule(CssGrammar.uri))
        .matches("url(\"http://www.example.com/pinkish.png\")")
        .matches("url(\"yellow\")");
  }

  @Test
  public void counter() {
    assertThat(b.rule(CssGrammar.any))
        .matches("counter(par-num, upper-roman)");
  }

  @Test
  public void color() {
    assertThat(b.rule(CssGrammar.hash))
        .matches("#ff0000");

    assertThat(b.rule(CssGrammar.any))
    .matches("rgb(110%, 0%, 0%)")
    .matches("rgb(255,-10,0)")
    .matches("rgb(255,0,0)");
  }


}
