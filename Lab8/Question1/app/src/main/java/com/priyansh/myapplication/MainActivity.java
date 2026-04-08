package com.priyansh.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etTaskName, etDueDate;
    private Spinner spinnerPriority;
    private Button btnSave;
    private ListView listViewTasks;

    private DatabaseHelper dbHelper;
    private TaskAdapter adapter;
    private Task taskToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTaskName = findViewById(R.id.etTaskName);
        etDueDate = findViewById(R.id.etDueDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        btnSave = findViewById(R.id.btnSave);
        listViewTasks = findViewById(R.id.listViewTasks);

        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> saveTask());

        loadTasks();
    }

    private void loadTasks() {
        List<Task> tasks = dbHelper.getAllTasks();
        if (adapter == null) {
            adapter = new TaskAdapter(this, tasks, new TaskAdapter.OnTaskActionListener() {
                @Override
                public void onEdit(Task task) {
                    prepareEdit(task);
                }

                @Override
                public void onDelete(Task task) {
                    dbHelper.deleteTask(task.getId());
                    loadTasks();
                    Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                }
            });
            listViewTasks.setAdapter(adapter);
        } else {
            adapter.updateTasks(tasks);
        }
    }

    private void saveTask() {
        String name = etTaskName.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();
        String priority = spinnerPriority.getSelectedItem().toString();

        if (name.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (taskToEdit == null) {
            // Add new task
            Task newTask = new Task(name, dueDate, priority);
            dbHelper.addTask(newTask);
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing task
            taskToEdit.setName(name);
            taskToEdit.setDueDate(dueDate);
            taskToEdit.setPriority(priority);
            dbHelper.updateTask(taskToEdit);
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
            taskToEdit = null;
            btnSave.setText("Save Task");
        }

        clearFields();
        loadTasks();
    }

    private void prepareEdit(Task task) {
        taskToEdit = task;
        etTaskName.setText(task.getName());
        etDueDate.setText(task.getDueDate());
        
        // Set spinner selection
        String[] priorities = getResources().getStringArray(R.array.priority_levels);
        for (int i = 0; i < priorities.length; i++) {
            if (priorities[i].equals(task.getPriority())) {
                spinnerPriority.setSelection(i);
                break;
            }
        }
        
        btnSave.setText("Update Task");
    }

    private void clearFields() {
        etTaskName.setText("");
        etDueDate.setText("");
        spinnerPriority.setSelection(0);
    }
}
