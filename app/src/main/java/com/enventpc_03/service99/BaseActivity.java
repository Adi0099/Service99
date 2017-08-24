
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
