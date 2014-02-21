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
package org.sonar.css.checks;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-all-gradient-definitions
 * @author tkende
 *
 */
@Rule(key = "gradients", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class AllGradientDefinitions extends SquidCheck<LexerlessGrammar> {

  private static List<String> gradients = ImmutableList.<String> of(
      "-ms-(linear|radial)-gradient.*",
      "-moz-(linear|radial)-gradient.*",
      "-o-(linear|radial)-gradient.*",
      "-webkit-(linear|radial)-gradient.*",
      "-webkit-gradient.*");

  private static String gradientMatcher = "-(ms|o|moz|webkit)-.*gradient.*";
  private List<String> gradientsFound;

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule, CssGrammar.declaration);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.ruleset) || astNode.is(CssGrammar.atRule)) {
      gradientsFound = new ArrayList<String>(gradients);
    } else if (astNode.is(CssGrammar.declaration)) {
      String value = astNode.getFirstChild(CssGrammar.value).getTokenValue();
      if (value.matches(gradientMatcher)) {
        removeMatch(value);
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode != null) {
      if (astNode.is(CssGrammar.ruleset)) {
        if (gradientsFound.size() != gradients.size()) {
          for (String exptected : gradientsFound) {
            getContext().createLineViolation(this, "Missing gradient: " + exptected, astNode);
          }
        }
      }
    }
  }

  private void removeMatch(String value) {
    Iterator<String> gradientIt = gradientsFound.iterator();
    while (gradientIt.hasNext()) {
      if (value.matches(gradientIt.next())) {
        gradientIt.remove();
      }
    }
  }

}
