package com.point2points.fishregister.Search.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.point2points.fishregister.EmployeeDetails.activity.EmployeeDetailsActivity;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Search.adapter.SearchAdapter;
import com.point2points.fishregister.Search.model.Employee;
import com.point2points.fishregister.Search.provider.SearchProvider;
import com.point2points.fishregister.Search.webapi.HttpHelper;
import com.point2points.fishregister.Utilities.DBHelper;
import com.point2points.fishregister.Utilities.ItemClickSupport;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private int mLoaderId = 9301;
    private Cursor mCursor;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchAdapter mAdaper;
    private Toolbar mToolbar;
    private TextView mTxtTitleToolbar;

    private MaterialSearchView mSearchView;
    private String mSelection;
    private String[] mSelectionArgs;
    private ArrayList<Employee> mEmployee = new ArrayList<>();

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupToolBar();

        mLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mAdaper = new SearchAdapter(SearchActivity.this, mCursor);

        mRecyclerView = findViewById(R.id.searchRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdaper);

        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(mQueryTextListener);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(mItemClickListener);
    }

    private void setupToolBar() {
        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mTxtTitleToolbar = findViewById(R.id.txtTitle);
        mTxtTitleToolbar.setText(getString(R.string.title_activity_search));
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbHelper = new DBHelper(SearchActivity.this);
        HttpHelper.getAllUser(SearchActivity.this, TAG);
        getSupportLoaderManager().initLoader(mLoaderId, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mLoaderId == id) {
            return new CursorLoader(SearchActivity.this, SearchProvider.CONTENT_URI, null, mSelection, mSelectionArgs, null);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mLoaderId == loader.getId()) {
            if (data != null) {
                mCursor = data;
                mAdaper.swapCursor(mCursor);

                for (int i = 0; i < mCursor.getCount(); i++) {
                    Employee employee = Employee.getEmployee(data, i);
                    mEmployee.add(employee);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        mSearchView.setVisibility(View.VISIBLE);
        return super.onCreateOptionsMenu(menu);
    }

    private ItemClickSupport.OnItemClickListener mItemClickListener = new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
            Intent intent = new Intent(SearchActivity.this, EmployeeDetailsActivity.class);
            intent.putExtra("DATA", mEmployee.get(position));
            startActivity(intent);
        }
    };

    MaterialSearchView.OnQueryTextListener mQueryTextListener = new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (query.equals("")) {
                mSelection = null;
                mSelectionArgs = null;
            } else {
                mSelection = DBHelper.EMPLOYEE_COLUMN_SERIAL_NUMBER + " LIKE ? ";
                mSelectionArgs = new String[]{"%" + query + "%"};
            }
            SearchActivity.this.getSupportLoaderManager().restartLoader(mLoaderId, null, SearchActivity.this);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
}
