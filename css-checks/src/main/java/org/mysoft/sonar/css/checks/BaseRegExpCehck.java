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
package org.mysoft.sonar.css.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liux09 on 14-2-21.
 */
public abstract class BaseRegExpCehck extends SquidCheck<LexerlessGrammar> {

    protected static final String DEFAULT_REG_EXPRESSION = "^((?<=[a-z]))[0-9a-z_-]+$";
    @RuleProperty(
            key = "reg_expressiosn",
            defaultValue = DEFAULT_REG_EXPRESSION)
    public String regExpression = DEFAULT_REG_EXPRESSION;

    protected abstract CssGrammar getIdentityType();

    protected abstract String getViolationMessage(AstNode node);

    @Override
    public void visitNode(AstNode astNode) {
       AstNode identityNode = astNode.getLastChild();
        if (identityNode.getType() != getIdentityType()) return;

        Pattern pattern = Pattern.compile(regExpression);
        Matcher matcher = pattern.matcher(identityNode.getTokenValue());
        if (!matcher.find()) {
            String message = getViolationMessage(identityNode);
            getContext().createLineViolation(this, message, astNode);
        }
    }
}
