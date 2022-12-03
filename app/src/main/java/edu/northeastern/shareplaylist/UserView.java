package edu.northeastern.shareplaylist;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

        import entity.User;

public class UserView extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private ArrayList<User> userList;
    private UserAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview);

        userList = new ArrayList<>();
        adapter = new UserAdapter(this, userList);

        ref = FirebaseDatabase.getInstance().getReference();
        userRecyclerView = findViewById(R.id.RecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();

        // display other users of the app in recyclerview when current user logs in
        ref.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    User currentUser = userSnapshot.getValue(User.class);
                    if(!currentUser.getUid().equals(mAuth.getCurrentUser().getUid())){
                        userList.add(currentUser);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}