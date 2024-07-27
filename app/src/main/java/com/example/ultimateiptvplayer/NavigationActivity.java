package com.example.ultimateiptvplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ultimateiptvplayer.Channels.Channel;
import com.example.ultimateiptvplayer.Fragments.Categorie.CategorieFragment;
import com.example.ultimateiptvplayer.Fragments.Categorie.OnCategoriesListener;
import com.example.ultimateiptvplayer.Fragments.Channels.ChannelQualityFragment;
import com.example.ultimateiptvplayer.Fragments.Channels.ChannelsFragment;
import com.example.ultimateiptvplayer.Fragments.Channels.OnChannelListener;
import com.example.ultimateiptvplayer.Fragments.Channels.OnQualityListener;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class NavigationActivity extends AppCompatActivity implements OnCategoriesListener, OnChannelListener, OnFullScreenListener, OnResetPlaylistListener, OnQualityListener {
    private PlaylistsManager playlistManager;
    private CategorieFragment categorieFragment;
    private ChannelsFragment channelsFragment;

    private PlayerFragment playerFragment_viewer;


    private String currentCategory;
    private Channel currentChannel;
    private QUALITY currentCategorieQuality;
    private TreeMap<QUALITY, ArrayList<Channel>> currentChannelsByQuality;

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
        playerFragment_viewer = new PlayerFragment(getApplicationContext(),this);
        getSupportFragmentManager().beginTransaction().replace(R.id.player_fragment_viewer, playerFragment_viewer).commit();

        //Create the header fragment
        HeaderFragment headerFragment = new HeaderFragment(this, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.header_fragment, headerFragment).commit();

        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(playerInFullScreen){
                    System.out.println("BACK TO NORMAL SCREEN");
                    setFullScreen(false);
                }else{
                    System.out.println("BACK TO QUALITY FRAGMENT");
                    backToQualityFragment();
                }
            }
        });
    }

    /**
     * Method to go back to set back the quality fragment
     */
    private void backToQualityFragment() {
        //rebuild the quality fragment
        channelQualityFragment = new ChannelQualityFragment(this.currentChannelsByQuality,this);
        getSupportFragmentManager().beginTransaction().replace(R.id.channel_navigation_fragment, channelQualityFragment).commit();
    }

    private ChannelQualityFragment channelQualityFragment;

    /**
     * Method to build the TreeMap of channels by quality
     * @return TreeMap of channels by quality
     */
    private void buildChannelsByQuality(){
        TreeMap<QUALITY, ArrayList<Channel>> channelsByQuality = new TreeMap<>();
        for(Channel channel : playlistManager.getCurrentPlaylist().getChannelsByCategoryName(currentCategory)){
            if(channelsByQuality.containsKey(channel.getQuality())){
                channelsByQuality.get(channel.getQuality()).add(channel);
            }else{
                ArrayList<Channel> channels = new ArrayList<>();
                channels.add(channel);
                channelsByQuality.put(channel.getQuality(), channels);
            }
        }
        this.currentChannelsByQuality = channelsByQuality;
    }

    /**
     * Method to handle the click on a category
     * @param categoryName the name of the category clicked
     */
    @Override
    public void onCategoriesClick(String categoryName) {
        this.currentCategory = categoryName;
        buildChannelsByQuality();
        channelQualityFragment = new ChannelQualityFragment(this.currentChannelsByQuality,this);
        getSupportFragmentManager().beginTransaction().replace(R.id.channel_navigation_fragment, channelQualityFragment).commit();
    }

    /**
     * Method to handle the click on a channel
     * @param position
     */
    @Override
    public void onChannelClick(int position) {
        //if the same channel is currently playing, set it in full screen
        if(this.currentChannel == this.currentChannelsByQuality.get(this.currentCategorieQuality).get(position) ){
            if(!this.playerInFullScreen()) {this.setFullScreen(true);}
        }
        //else, play the channel
        else{
            this.currentChannel = this.currentChannelsByQuality.get(this.currentCategorieQuality).get(position);
            playerFragment_viewer.setCurrentChannelUrl(this.currentChannel.getUrl());
            if(this.playerFragment_viewer.playerReady()){playerFragment_viewer.playChannel();}
        }
    }

    @Override
    public void onChannelLongClick(int position) {
        //show a dialog to add the channel to the favorite
        System.out.println("LONG CLICK ON CHANNEL");
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

    @Override
    public void onResetPlaylist() {
        System.out.println("CALL RESET PLAYLIST");
        // DELETE CURRENT PLAYLIST
        this.playlistManager.deleteCurrentPlaylist();
        // SWITCH TO ACTIVITY
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onQualityClick(int position,QUALITY quality) {
        this.currentCategorieQuality = quality;
        //Generate the channel fragment with the correct quality
        ChannelsFragment channelsFragment = new ChannelsFragment(this.currentChannelsByQuality.get(quality),this);
        getSupportFragmentManager().beginTransaction().replace(R.id.channel_navigation_fragment, channelsFragment).commit();
    }
}
