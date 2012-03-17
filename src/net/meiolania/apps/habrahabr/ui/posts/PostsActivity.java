/*
   Copyright (C) 2011 Andrey Zaytsev <a.einsam@gmail.com>
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
  
        http://www.apache.org/licenses/LICENSE-2.0
  
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package net.meiolania.apps.habrahabr.ui.posts;

import net.meiolania.apps.habrahabr.ApplicationFragmentActivity;
import net.meiolania.apps.habrahabr.R;
import net.meiolania.apps.habrahabr.ui.dashboard.DashboardActivity;
import net.meiolania.apps.habrahabr.ui.widgets.ActionBarHomeAction;
import net.meiolania.apps.habrahabr.utils.UIUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.markupartist.android.widget.ActionBar;

public class PostsActivity extends ApplicationFragmentActivity{
    public final static String LINK_MAIN = "http://habrahabr.ru/blogs/";
    public final static String LINK_NEW = "http://habrahabr.ru/new/";
    public final static String LINK_BEST = "http://habrahabr.ru/top/";
    private String link = "http://habrahabr.ru/blogs/";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            link = extras.getString("link");
        
        setActionBar();
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        PostsFragment postsFragment = new PostsFragment();
        postsFragment.setLink(link);

        fragmentTransaction.add(R.id.posts_list_fragment, postsFragment);

        fragmentTransaction.commit();

        if(!UIUtils.isTablet(this) && !preferences.isUseTabletDesign()){
            FrameLayout postsShowFrameLayout = (FrameLayout) findViewById(R.id.post_show_fragment);
            postsShowFrameLayout.setVisibility(View.GONE);
        }
    }

    private void setActionBar(){
        if(!UIUtils.isHoneycombOrHigher()){
            ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
            
            if(link.equals(LINK_MAIN))
                actionBar.setTitle(R.string.main_posts);
            else if(link.equals(LINK_NEW))
                actionBar.setTitle(R.string.new_posts);
            else if(link.equals(LINK_BEST))
                actionBar.setTitle(R.string.best_posts);
            else
                actionBar.setTitle(R.string.posts);
            
            actionBar.setHomeAction(new ActionBarHomeAction(this, preferences));
        }else{
            ActionBar actionBarView = (ActionBar) findViewById(R.id.actionbar);
            actionBarView.setVisibility(View.GONE);

            android.app.ActionBar actionBar = getActionBar();
            
            if(link.equals(LINK_MAIN))
                actionBar.setTitle(R.string.main_posts);
            else if(link.equals(LINK_NEW))
                actionBar.setTitle(R.string.new_posts);
            else if(link.equals(LINK_BEST))
                actionBar.setTitle(R.string.best_posts);
            else
                actionBar.setTitle(R.string.posts);

            actionBar.setDisplayHomeAsUpEnabled(true);
            if(UIUtils.isIceCreamOrHigher())
                actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected Intent getActionBarIntent(){
        final Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

}