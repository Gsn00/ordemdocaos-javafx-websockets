package gui.services;


import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.JSONService;

public class InventoryService
{

	private TableView<String> table;
	private TextField txt;
	private TableColumn<String, String> columnItem;
	private TableColumn<String, String> columnButton;

	private static ObservableList<String> items = FXCollections.observableArrayList();

	public InventoryService(TableView<String> table, TextField txt, TableColumn<String, String> columnItem,
			TableColumn<String, String> columnButton)
	{
		this.table = table;
		this.txt = txt;
		this.columnItem = columnItem;
		this.columnButton = columnButton;
		configAll();
	}

	public TableView<String> getTable()
	{
		return table;
	}

	public TextField getTxt()
	{
		return txt;
	}

	public static ObservableList<String> getItems()
	{
		return items;
	}

	public void addItem(String item)
	{
		items.add(txt.getText());
		table.setItems(items);
		JSONService.setData("items", items);
	}

	public void removeItem(int item)
	{
		items.remove(item);
		table.setItems(items);
		JSONService.setData("items", items);
	}

	public void configAll()
	{
		@SuppressWarnings("unchecked")
		List<String> strList = (List<String>) JSONService.getList("items");
		items.addAll(strList);
		table.setItems(items);
		
		txt.setOnAction(event -> {
			addItem(txt.getText());
			txt.setText("");
		});
		
		columnItem.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnButton.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnButton.setCellFactory(param -> new TableCell<String, String>()
		{
			@Override
			protected void updateItem(String item, boolean empty)
			{
				super.updateItem(item, empty);
				if (item == null)
				{
					setGraphic(null);
					return;
				}
				Button bt = new Button("X");
				bt.setStyle("-fx-background-color:  #4f4f4f; -fx-text-fill: white");
				bt.setOpacity(0.4);
				int tableItem = getTableRow().getIndex();
				bt.setOnAction(e ->
				{
					removeItem(tableItem);
					table.setVisible(false);
					table.setVisible(true);
				});
				setGraphic(bt);
				setAlignment(Pos.CENTER);
			}
		});

		table.setPlaceholder(new Label("MOCHILA VAZIA"));
	}
}
