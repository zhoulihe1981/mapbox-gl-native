package com.mapbox.mapboxsdk.testapp.activity.maplayout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.testapp.R;

/**
 * Test activity showcasing showing a Map inside of a DialogFragment.
 * <p>
 * Uses the deprecated TextureView API to workaround the issue of seeing a grey background before the gl surface.
 * </p>
 */
public class MapInDialogActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_in_dialog);

    Button button = findViewById(R.id.button_open_dialog);
    button.setOnClickListener(view -> {
      FragmentManager fm = getSupportFragmentManager();
      MapDialogFragment editNameDialogFragment = MapDialogFragment.newInstance("Map Dialog");
      editNameDialogFragment.show(fm, "fragment_dialog_map");
    });
  }

  public static class MapDialogFragment extends DialogFragment {

    private MapView mapView;

    public MapDialogFragment() {
    }

    public static MapDialogFragment newInstance(String title) {
      MapDialogFragment frag = new MapDialogFragment();
      Bundle args = new Bundle();
      args.putString("title", title);
      frag.setArguments(args);
      return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dialog_map, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapView = view.findViewById(R.id.mapView);
      mapView.onCreate(savedInstanceState);
      mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.OUTDOORS));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      return new Dialog(getActivity(), getTheme()) {
        @Override
        public void dismiss() {
          if (mapView != null && !mapView.isDestroyed()) {
            mapView.onPause();
            mapView.onStop();
            mapView.onDestroy();
            mapView = null;
          }
          super.dismiss();
        }
      };
    }

    @Override
    public void onStart() {
      super.onStart();
      mapView.onStart();
    }

    @Override
    public void onResume() {
      super.onResume();
      mapView.onResume();
    }

    @Override
    public void onPause() {
      super.onPause();
      if (mapView != null) {
        mapView.onPause();
      }
    }

    @Override
    public void onStop() {
      super.onStop();
      if (mapView != null) {
        mapView.onStop();
      }
    }

    @Override
    public void onDestroyView() {
      super.onDestroyView();
      if (mapView != null) {
        mapView.onDestroy();
      }
    }

    @Override
    public void onLowMemory() {
      super.onLowMemory();
      if (mapView != null) {
        mapView.onLowMemory();
      }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      if (mapView != null) {
        mapView.onSaveInstanceState(outState);
      }
    }
  }
}