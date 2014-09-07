package scottso.assist911;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

public class TutorialActivity extends FragmentActivity {

    private TutorialPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mAdapter = new TutorialPagerAdapter(this.getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(mAdapter);

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator)findViewById(R.id.circles);
        circlePageIndicator.setViewPager(pager);

        MyActivity.EDITOR.putBoolean("FIRST_OPEN", false).commit();
    }

    private class TutorialPagerAdapter extends FragmentPagerAdapter {
        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FirstTutorialFragment();
                case 1:
                    return new SecondTutorialFragment();
                default:
                    return new FirstTutorialFragment();
            }
        }
    }
}
