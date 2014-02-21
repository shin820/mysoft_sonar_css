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
import org.mysoft.sonar.css.checks.*;

import java.util.Collection;

public final class CheckList {

    public static final String REPOSITORY_KEY = "css";

    public static final String REPOSITORY_NAME = "Sonar";

    private CheckList() {
    }

    @SuppressWarnings("rawtypes")
    public static Collection<Class> getChecks() {
        return ImmutableList.<Class>of(
        /*Mysoft rules begin*/
                ClassSelectorRegExpCheck.class,
                IdSelectorRegExpCheck.class,
                TypeSelectorRegExpCheck.class,
                PropertyNameRegExpCheck.class,
        /*Mysoft rules end*/

                AllGradientDefinitions.class,
                BulletproofFontFace.class,
                BewareOfBoxModel.class,
                CompatibleVendorPrefixes.class,
                DisallowDuplicateBackgroundImages.class,
                DisallowEmptyRules.class,
                DisallowIdsInSelectors.class,
                DisallowImport.class,
                DisallowImportant.class,
                DisallowOverqualifiedElements.class,
                DisallowOverspecificSelectors.class,
                DisallowSelectorsLikeRegEx.class,
                DisallowStarHack.class,
                DisallowUnderscoreHack.class,
                DisallowUnitsForZeroValues.class,
                DisallowUniversalSelector.class,
                DisplayPropertyGrouping.class,
                DuplicateProperties.class,
                KnownProperties.class,
                ShorthandProperties.class,
                TooManyWebFonts.class,
                VendorPrefixWithStandard.class
        );
    }

}

