/*
 * Copyright (c) 2013, Paul Blankenbaker
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package imgcv.yellowcube;

import img.core.filters.MatFilter;
import img.ui.LiveView;

/**
 * A base class that allows you to quickly build a test tool to exercise a
 * single filter by continually feeding data to the filter.
 *
 * <p>
 * This class provides the foundation for creating a GUI that allows you to test
 * your image filter without having to deal with a lot of the overhead of
 * setting up your own test tool. It works in the following manner:
 * </p>
 *
 * <ul>
 * <li>You extend (or create a instance) of this class.</li>
 * <li>At a minimum you invoke the {@link #setFilter()} method to set the
 * {@link MatFilter} implementation you want to test.</li>
 * <li>You construct a instance of your new class and then invoke the
 * {@link #main} method to show the tool.</li>
 * </ul>
 *
 * <p>
 * NOTE: You can run this class by itself (in which case it displays the
 * original image un-filtered).
 * </p>
 * 
 * <p>
 * The {@link com.techhounds.imgcv.tools.LiveView2013} class provides a example
 * of extending this class.
 * </p>
 *
 * @author pkb
 */
public class YellowView extends LiveView {

	public YellowView() {
		super("Yellow");
		setFilter(new PrimaryFilter());
	}
	
	public static void main(String[] args) {
		LiveView view = new YellowView();
		view.run();
	}
}
