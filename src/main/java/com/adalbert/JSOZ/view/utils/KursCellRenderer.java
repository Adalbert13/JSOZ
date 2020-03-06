package com.adalbert.JSOZ.view.utils;
 
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.adalbert.JSOZ.model.Kurs;

@SuppressWarnings("serial")
public class KursCellRenderer extends JLabel implements ListCellRenderer<Kurs> {
 
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
	
    @Override
    public Component getListCellRendererComponent(JList<? extends Kurs> list, Kurs kurs, int index, boolean isSelected, boolean cellHasFocus) {
        setText("Nazwa kursu: " + kurs.getNazwa() + "      Słowa kluczowe: " + kurs.getSlowaKluczowe());     
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        if (isSelected) {
        	setBackground(list.getSelectionBackground());
        	setForeground(list.getSelectionForeground());
        } else {
        	setBackground(list.getBackground());
        	setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        if (cellHasFocus)
        	setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
        else
        	setBorder(noFocusBorder);
        return this;
    }

     
}