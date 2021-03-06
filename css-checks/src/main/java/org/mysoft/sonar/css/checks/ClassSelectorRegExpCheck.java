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
import org.sonar.check.*;
import org.sonar.css.checks.CheckList;
import org.sonar.css.parser.CssGrammar;

/**
 * mysoft rule : 类选择器正则匹配检查
 *
 * @author liux09
 */
@Rule(key = "ClassSelectorRegExpCheck",
        name = "Mysoft rule : Class Selector Check by Regex Expression",
        description = "通过正则表达式指定类选择器的合法格式",
        priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class ClassSelectorRegExpCheck extends BaseRegExpCehck {

    @Override
    public void init() {
        subscribeTo(CssGrammar.classSelector);
    }

    @Override
    protected CssGrammar getIdentityType() {
        return CssGrammar.identNoWS;
    }

    @Override
    protected String getViolationMessage(AstNode node) {
        return "类选择器" + node.getTokenValue() + "与正则表达式" + regExpression + "不匹配";
    }
}
