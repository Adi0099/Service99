
        setContentView(R.layout.navigation_drawer_base_layout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //    handleIntent(getIntent());

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _items = new ArrayList<Items>();
        _items.add(new Items("Home", R.drawable.home));
        _items.add(new Items("Book Service", R.drawable.book));
        _items.add(new Items("Track Ticket", R.drawable.track));
        _items.add(new Items("Notifications/Offers", R.drawable.notification));
        _items.add(new Items("Contact Us", R.drawable.contact));
        _items.add(new Items("Exit", R.drawable.ic_exit1));

//		//Adding header on list view
//		View header = (View)getLayoutInflater().inflate(R.layout.list_view_header_layout, null);
//		mDrawerList.addHeaderView(header);

        // set up the drawer's list view with items and click listener

        mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });


        // Change Action Bar Background Color "may be not works"
       getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D2691E")));

        // enable ActionBar app icon to behave as action to toggle nav drawer

//        getSupportActionBar().setHomeEnabled(true); // for burger icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // burger icon related
        getSupportActionBar().setDisplayShowCustomEnabled(true); // CRUCIAL - for displaying your custom actionbar

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hm2);


        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.drawable.icon11,     /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,       /* "open drawer" description for accessibility */
                R.string.close_drawer)      /* "close drawer" description for accessibility */

  /*mDrawerToggle = new ActionBarDrawerToggle(
          getActivity(),
          mDrawerLayout,
          R.string.navigation_drawer_open,
          R.string.navigation_drawer_close
)
  * */ {
            @Override
            public void onDrawerClosed(View drawerView) {
//                getSupportActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        /**
         * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
         * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
         * */
        if (isLaunch) {
            /**
             *Setting this flag false so that next time it will not open our first activity.
             *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
             *In this case this base activity will always be call when any child activity will launch.
             */
            isLaunch = false;
            openActivity(0);
        }

    }




    /**
     * @param position Launching activity when any list item is clicked.
     */
    protected void openActivity(int position) {

        mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
//            case 0:
//                startActivity(new Intent(this,Login.class));
//                break;
            case 0:
                startActivity(new Intent(this, ServiceRequest.class));
                break;
            case 1: {

                startActivity(new Intent(this, Track_Ticket.class));
                break;
            }
            case 2:
                startActivity(new Intent(this, Notification1.class));
                break;
            case 3:
                startActivity(new Intent(this, Contact_Us.class));
                break;

            case 4:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Exit Application?");
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        Process.killProcess(Process.myPid());
                                        System.exit(1);
                                    }
                                })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;

            default:

                break;
        }

//		Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
//         Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main, menu);


                return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.refresh:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

}
