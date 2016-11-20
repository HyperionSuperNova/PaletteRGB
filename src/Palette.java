import javafx.scene.paint.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by stryker on 18/11/16.
 */
public class Palette {
    public Vue view;
    public Modele mod;
    Controleur controle;
    public Palette(){
        controle = new Controleur();
        view = new Vue();
        view.setVisible(true);
        mod = new Modele();
    }

    class Vue extends JFrame{
        Container conteneur;
        JSlider slide1,slide2,slide3;
        JMenuBar mybar;
        JMenu selection;
        JMenuItem gris50;
        JMenuItem compl;
        public Vue(){
            this.setTitle("Palette");
            this.setMinimumSize(new Dimension(1024,720));
            this.conteneur = getContentPane();
            this.conteneur.setLayout(new GridLayout(0,2));
            mybar = new JMenuBar();
            mybar.setSize(100,100);
            JPanel panneauChoix = new JPanel(new BorderLayout());
            JPanel colorPanel = new JPanel(new BorderLayout());
            panneauChoix.setLayout(new GridLayout(4,0));
            this.slide1 = new JSlider(0,255);
            this.slide2 = new JSlider(0,255);
            this.slide3 = new JSlider(0,255);
            ArrayList<JSlider> slide = new ArrayList<JSlider>(3);
            slide.add(slide1);
            slide.add(slide2);
            slide.add(slide3);
            slide.get(0).setBorder(BorderFactory.createTitledBorder("Red"));
            slide.get(1).setBorder(BorderFactory.createTitledBorder("Blue"));
            slide.get(2).setBorder(BorderFactory.createTitledBorder("Green"));
            for (JSlider a : slide) {
                a.setMajorTickSpacing(25);
                a.setPaintTicks(true);
                a.setPaintLabels(true);
                a.setPaintTrack(true);
                a.setVisible(true);
                a.setValue(0);
                a.addChangeListener(new Controleur());
                panneauChoix.add(a);
            }
            this.conteneur.add("ChoicePanel",panneauChoix);
            this.conteneur.add("ColorPanel",colorPanel);
            selection = new JMenu("Selection");
            gris50 = new JMenuItem("Gris50");
            compl = new JMenuItem("Complementaire");
            selection.addActionListener(new Controleur());
            gris50.addActionListener(new Controleur());
            compl.addActionListener(new Controleur());
            selection.add(compl);
            selection.add(gris50);
            mybar.add(selection);
            this.setJMenuBar(mybar);
        }

        public void miseAjour(){
            this.conteneur.getComponent(1).setBackground(new Color(mod.red,mod.green,mod.blue));
            this.conteneur.getComponent(1).repaint();
        }

        public void majGris50(){
            this.conteneur.getComponent(1).setBackground(new Color(255/2,255/2,255/2));
            this.conteneur.getComponent(1).repaint();
        }

        public void complementaire(){
            Color a = this.conteneur.getComponent(1).getBackground();
            Color b = new Color(slide1.getValue() ,slide3.getValue(), slide2.getValue());
            this.conteneur.getComponent(1).setBackground(b);
            this.conteneur.getComponent(1).repaint();
        }

        public Container getConteneur() {
            return conteneur;
        }

        public void setConteneur(Container conteneur) {
            this.conteneur = conteneur;
        }


    }

    class Modele{
        public int red,green,blue;

        public Modele() {
            this.red = 0;
            this.blue = 0;
            this.green = 0;
        }

        public Modele(int red, int green, int blue){
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public void setGreen(int green) {
            this.green = green;
        }

        public void setBlue(int blue) {
            this.blue = blue;
        }

        public void setColors(Color a){
            this.red = a.getRed();
            this.blue = a.getBlue();
            this.green = a.getGreen();
        }
    }

    class Controleur implements ChangeListener,ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == view.gris50){
                view.slide3.setValue(255/2);
                view.slide2.setValue(255/2);
                view.slide1.setValue(255/2);
                view.majGris50();
            }else if(e.getSource() == view.compl){
                Color a = view.conteneur.getComponent(1).getBackground();
                view.slide3.setValue(100 - a.getGreen());
                view.slide2.setValue(100 - a.getBlue());
                view.slide1.setValue(100 - a.getRed());
                view.complementaire();
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            mod.setRed(view.slide1.getValue());
            mod.setGreen(view.slide3.getValue());
            mod.setBlue(view.slide2.getValue());
            view.miseAjour();
        }
    }

    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            Palette random = new Palette();
        });
    }                                                           

}
