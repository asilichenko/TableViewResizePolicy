import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexey Silichenko (a.silichenko@gmail.com)
 * created on 23.10.2017
 */
public class ViewController implements Initializable {

    private static final int RIGHT_PADDING = 10;

    @FXML
    private AnchorPane root;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn staticCol;

    @FXML
    private Line l1;
    @FXML
    private Line l2;
    @FXML
    private Line l3;
    @FXML
    private Line l4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final double padding = staticCol.getWidth() + RIGHT_PADDING;
        table.setColumnResizePolicy(param -> {
            final double totalWidth = table.getWidth() - padding;
            final double colWidth = totalWidth / (table.getColumns().size() - 1);

            placeLine(l1, colWidth, 1);
            placeLine(l2, colWidth, 4);
            placeLine(l3, colWidth, 9);
            placeLine(l4, colWidth, 14);

            double sum = 0;
            sum = resize(colWidth, sum, 0, 1);
            sum = resize(colWidth, sum, 1, 3);
            sum = resize(colWidth, sum, 4, 5);
            sum = resize(colWidth, sum, 9, 5);
            resize(colWidth, sum, 14, 3);

            return true;
        });
    }

    private void placeLine(Line line, double colWidth, int offset) {
        line.setStartX(staticCol.getWidth() + colWidth * offset);
        line.setEndX(line.getStartX());
    }

    /**
     * Calculates column width as integer based on col position and col group size
     *
     * @param w0        expected column width
     * @param sum       sum of real columns width
     * @param offset    columns count group offset
     * @param groupSize number of columns in this group
     * @return new sum
     */
    private double resize(double w0, double sum, int offset, int groupSize) {
        final double groupWidth = w0 * (offset + groupSize) - sum;
        double groupSum = 0;
        for (int i = 1; i <= groupSize; i++) {
            final double w1 = Math.floor((groupWidth - groupSum) / (groupSize + 1 - i));
            groupSum += w1;
            table.getColumns().get(offset + i).setPrefWidth(w1);
        }
        return sum + groupSum;
    }

    @FXML
    public void decrease() {
        final Window window = root.getScene().getWindow();
        final double width = window.getWidth();
        window.setWidth(width - 1);
    }

    @FXML
    public void increase() {
        final Window window = root.getScene().getWindow();
        final double width = window.getWidth();
        window.setWidth(width + 1);
    }
}
