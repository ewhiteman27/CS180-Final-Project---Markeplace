String[] editProfile = new String[3];
                    editProfile[0] = "Change Username";
                    editProfile[1] = "Change Password";
                    editProfile[2] = "Change Email";

                    String editChoice = receive.readLine(); //receiver 06

                    if (editChoice.equals(editProfile[0])) { //username change

                        String emailStored = receive.readLine();
                        //receiver 07

                        String passwordStored = receive.readLine();
                        //receiver 08

                        String newUsernameStored = receive.readLine();
                        //receiver 09

                        boolean changeUsername = edits.changeUsername(newUsernameStored, passwordStored, emailStored);
                        String confirmChange;

                        if (changeUsername) {
                            confirmChange = "true";
                        } else {
                            confirmChange = "false";
                        }

                        send.println(confirmChange);
                        send.flush();
                        //sender 13

                    } else if (editChoice.equals(editProfile[1])) {  //password change
                        String emailStored = receive.readLine();
                        //receiver 10

                        String usernameStored = receive.readLine();
                        //receiver 11

                        String newPasswordStored = receive.readLine();
                        //receiver 12
                        try {
                            boolean changePassword = edits.changePassword(newPasswordStored, usernameStored, emailStored);
                            String confirmChange;

                            if (changePassword) {
                                confirmChange = "true";
                            } else {
                                confirmChange = "false";
                            }

                            send.println(confirmChange);
                            send.flush();
                            //sender 14
                        } catch (Exception e) {
                            String confirmChange = "false";
                            send.println(confirmChange);
                            send.flush();
                            //sender 14
                        }

                    } else if (editChoice.equals(editProfile[2])) {  //emailchange
                        String usernameStored = receive.readLine();
                        //receiver 15

                        String passwordStored = receive.readLine();
                        //receiver 16

                        String newEmailStored = receive.readLine();
                        //receiver 17
                        String confirmChange;
                        try {
                            boolean changeEmail = edits.changeEmail(newEmailStored, usernameStored, passwordStored);

                            if (changeEmail) {
                                confirmChange = "true";
                            } else {
                                confirmChange = "false";
                            }

                        } catch (Exception e) {
                            confirmChange = "false";
                        }

                        send.println(confirmChange);
                        send.flush();
                        //sender 18

                    }
