package Menu_P;
import javax.swing.*;
import java.awt.*;

public class CambiarFuente {
    public static void cambiarFuenteGlobal(Font fuente) {
        UIManager.put("Menu.font", fuente);
        UIManager.put("MenuItem.font", fuente);
        UIManager.put("Label.font", fuente);
        UIManager.put("Button.font", fuente);
        UIManager.put("ComboBox.font", fuente);
        UIManager.put("Table.font", fuente);
        UIManager.put("TableHeader.font", fuente);
        UIManager.put("TextField.font", fuente);
        UIManager.put("TextArea.font", fuente);
        UIManager.put("CheckBox.font", fuente);
        UIManager.put("RadioButton.font", fuente);
        UIManager.put("TabbedPane.font", fuente);
        UIManager.put("ToolTip.font", fuente);
        UIManager.put("TitledBorder.font", fuente);
        UIManager.put("List.font", fuente);
    }
}
