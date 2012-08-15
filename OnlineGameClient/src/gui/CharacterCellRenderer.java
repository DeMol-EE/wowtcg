/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import model.CharacterInfo;

/**
 *
 * @author Warkst
 */
public class CharacterCellRenderer implements ListCellRenderer {
    private ArrayList<CharacterInfo> characters;

    public CharacterCellRenderer(ArrayList<CharacterInfo> characters) {
	this.characters = characters;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	CharacterInfo character = characters.get(index);

	Color bg = Color.WHITE;
	if(isSelected) bg = Color.BLUE;
	Color fg = Color.BLACK;
	if(isSelected) fg = Color.WHITE;

	JPanel characterPanel = new JPanel();
	characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
	characterPanel.setBackground(bg);
	characterPanel.setFocusable(false);

	JLabel nameLabel = new JLabel(character.getName());
	nameLabel.setForeground(fg);
	JLabel detailLabel = new JLabel("lvl "+character.getLevel()+" "+character.getRace()+" "+character.getClazz());
	detailLabel.setForeground(fg);
	JLabel areaLabel = new JLabel(character.getArea());
	areaLabel.setForeground(fg);
	
	characterPanel.add(nameLabel);
	characterPanel.add(detailLabel);
	characterPanel.add(areaLabel);

//	characterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

	return characterPanel;
    }
}
