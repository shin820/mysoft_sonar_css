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
package mysoft.sonar.css.checks;

import com.sonar.sslr.squid.checks.CheckMessagesVerifier;
import org.junit.Test;
import org.mysoft.sonar.css.checks.ClassSelectorRegExpCheck;
import org.mysoft.sonar.css.checks.PropertyNameRegExpCheck;
import org.sonar.css.checks.TestHelper;
import org.sonar.squid.api.SourceFile;

import java.io.File;

public class PropertyNameRegExpCheckTest {

    @Test
    public void Should_find_css_selector_violation() {
        PropertyNameRegExpCheck check = new PropertyNameRegExpCheck();
        SourceFile file = TestHelper.scanSingleFile(new File(
                "src/test/resources/mysoft.checks/PropertyNameRegExpCheck.css"), check);
        CheckMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(6).withMessage("属性名称_border与正则表达式" + check.regExpression + "不匹配")
                .next().atLine(7).withMessage("属性名称*color与正则表达式" + check.regExpression + "不匹配")
                .next().atLine(8).withMessage("属性名称MARGIN与正则表达式" + check.regExpression + "不匹配");
    }

}
