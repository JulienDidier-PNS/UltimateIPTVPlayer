package com.example.ultimateiptvplayer;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ultimateiptvplayer.Channels.Channel;
import com.example.ultimateiptvplayer.Fragments.Categorie.CategorieFragment;
import com.example.ultimateiptvplayer.Fragments.Categorie.OnCategoriesListener;
import com.example.ultimateiptvplayer.Fragments.Channels.ChannelsFragment;
import com.example.ultimateiptvplayer.Fragments.Channels.OnChannelListener;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;

public class NavigationActivity extends AppCompatActivity implements OnCategoriesListener, OnChannelListener, OnFullScreenListener {
    private boolean firstTime = false;
    private PlaylistsManager playlistManager;
    private CategorieFragment categorieFragment;
    private ChannelsFragment channelsFragment;

    private PlayerFragment playerFragment_viewer;

    private String currentCategory;
    private Channel currentChannel;

    private boolean playerInFullScreen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Create the playlist manager
        playlistManager = PlaylistsManager.getInstance(getApplicationContext());

        playlistManager.getCurrentPlaylist().updateChannels();

        // Create the categories fragment
        this.categorieFragment = new CategorieFragment(playlistManager.getCurrentPlaylist(),this);
        getSupportFragmentManager().beginTransaction().replace(R.id.categories_fragment, categorieFragment).commit();

        // Create the player fragment
        playerFragment_viewer = PlayerFragment.getInstance(getApplicationContext(),this);
        getSupportFragmentManager().beginTransaction().replace(R.id.player_fragment_viewer, playerFragment_viewer).commit();
    }

    @Override
    public void onCategoriesClick(String categoryName) {
        channelsFragment = new ChannelsFragment(playlistManager.getCurrentPlaylist().getChannelsByCategoryName(categoryName),this);
        getSupportFragmentManager().beginTransaction().replace(R.id.channel_navigation_fragment, channelsFragment).commit();
        this.currentCategory = categoryName;
    }

    @Override
    public void onChannelClick(int position) {
        //if the channel is currently playing, set it in full screen
        if(this.currentChannel == playlistManager.getCurrentPlaylist().getChannelsByCategoryName(currentCategory).get(position) ){
            if(!this.playerInFullScreen()) {
                this.setFullScreen(true);
            }
        }

        this.currentChannel = playlistManager.getCurrentPlaylist().getChannelsByCategoryName(currentCategory).get(position);
        playerFragment_viewer.setCurrentChannelUrl(this.currentChannel.getUrl());
        if(this.playerFragment_viewer.playerReady()){
            playerFragment_viewer.playChannel();
        }
    }

    private ConstraintLayout.LayoutParams initparams;

    @Override
    public void setFullScreen(boolean isFullScreen) {
        System.out.println("onFullScreen");
        System.out.println("fullscreen option : " + isFullScreen);
        playerInFullScreen = isFullScreen;
        FrameLayout player_fragment_viewer = findViewById(R.id.player_fragment_viewer);
        if(initparams == null){
            initparams = (ConstraintLayout.LayoutParams) player_fragment_viewer.getLayoutParams();
        }
        if(isFullScreen){
            player_fragment_viewer.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            this.playerInFullScreen = true;
        }else{
            player_fragment_viewer.setLayoutParams(initparams);
            this.playerInFullScreen = false;
        }
    }

    @Override
    public boolean playerInFullScreen() {
        return playerInFullScreen;
    }

}
