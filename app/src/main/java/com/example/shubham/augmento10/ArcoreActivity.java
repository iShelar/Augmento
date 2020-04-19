package com.example.shubham.augmento10;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import static com.example.shubham.augmento10.DetailActivity.EXTRA_NAME;

public class ArcoreActivity extends AppCompatActivity{

    private ArFragment arFragment;
    private ModelRenderable andyRenderable;
    private TransformableNode transformableNode;
    private FloatingActionButton scaleUpButton,scaleDownButton;
    private float minSize = 0.5f,maxSize = 1f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcore_camera);

        //Intent with name of model to fetch from local storage
        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_NAME);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        //file referencing to name of the model
        File file = new File("storage/emulated/0/Download/" + name);
        //File file = new File("storage/emulated/0/Download/model.sfb");

        scaleUpButton = findViewById(R.id.scaleUp);
        scaleDownButton = findViewById(R.id.scaleDown);
        scaleUpButton.setAlpha(0.8f);
        scaleDownButton.setAlpha(0.8f);

        Callable<InputStream> callable = () -> new FileInputStream(file);
        ModelRenderable
                .builder()
                .setSource(this,callable)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(throwable -> {
                    Toast toast =
                            Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });

        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
            if (andyRenderable == null) {
                return;
            }

            // Create the Anchor.
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            // Create the transformable andy and add it to the anchor.
            transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.getScaleController().setMaxScale(maxSize);
            transformableNode.getScaleController().setMinScale(minSize);
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(andyRenderable);
            transformableNode.select();

            scaleUpButton.setOnClickListener(v -> {
                scaleUpModel(transformableNode);
            });
            scaleDownButton.setOnClickListener(v -> {
                scaleDownModel(transformableNode);
            });

        });
    }

    private void scaleDownModel(TransformableNode transformableNode) {
        minSize-=0.5;
        maxSize-=0.5;
        transformableNode.getScaleController().setMaxScale(maxSize);
        transformableNode.getScaleController().setMinScale(minSize);
    }

    private void scaleUpModel(TransformableNode transformableNode) {
        minSize+=0.5;
        maxSize+=0.5;
        transformableNode.getScaleController().setMaxScale(maxSize);
        transformableNode.getScaleController().setMinScale(minSize);
    }

    /*private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {

        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }*/
}
