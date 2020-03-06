package com.adalbert.JSOZ.view.windows;

import java.awt.Point;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class WindowManager extends JFrame {

	private Stack<JPanel> views;
	
	public WindowManager() {
		this.views = new Stack<>();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(ViewFinals.WINDOW_SIZE);
		this.setLocation(new Point(50,50));
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void pushView(JPanel view) {
		if (getViews().size() > 0)
			remove(getViews().lastElement());
		add(view);
		getViews().push(view);
		revalidate();
		repaint();
	}
	
	public void popView() {
		if (getViews().isEmpty())
			throw new EmptyStackException();
		remove(getViews().pop());
		if (getViews().size() > 0)
			add(getViews().lastElement());
		revalidate();
		repaint();
	}

	public Stack<JPanel> getViews() {
		return views;
	}
}
