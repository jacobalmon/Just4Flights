# How to Use Version Control via Git

**Install Git In Eclipse**

- Open Eclipse
- Go to _Help_ -> _Elipse Marketplace_.
- In the search bar, type _EGit_.
- Install the _EGit - Git Integration for Eclipse_ plugin.

**Configure Git**

- Go to _Window_ -> _Preferences_.
- Expand _Team_ -> _Git_.
- In the _Configuration_ tab, click _Add Entry_ to set your Git username  and email.

**Clone a Repository from GitHub**

- In Eclipse, go to _File_ -> _Import_.
- Select _Git_ -> _Projects from Git_ and click _Next_.
- Choose _Clone URI_ and click _Next_.
- Enter your repository's GitHub URL in the _URI_ field, and your GitHub username.
- https://github.com/jacobalmon/SE-Project.git
- Before Clicking next we got to create a token through github.
- In GitHub, go to _Settings_.
- Select _Developer Settings_ -> _Personal Access Tokens_ -> _Tokens (Classic)_.
- Select _Generate New Token_.
- Add a Note, Set Expiration, and Select repo
- Click Generate Token and Copy the token link and keep it somewhere safe.
- Now enter the token link as the password. (Protocol: https)
- Choose branches you want to clone and clock Next.
- Import Project as General (3rd Option).

**Pushing Changes to GitHub**
- In the terminal go to the directory of the cloned repo.
- Make changes to whatever is needed.
- Enter _git add ._
- This will update the log of changes made.
- Enter _git commit -m "<insert message\>"_
- Create a comment of what you did to the code.
- Enter _git push origin <branch_name>_
- This will push the changes into GitHub for everyone to see.

**Updating Your Local Repository**
- In the terminal go to the directory of the cloned repo.
- Enter _git pull origin <branch_name>_
- This will pull the changes and update your repo.

If you have questions let me know. - Jacob

I would like to have a meeting sometime after the Proposal to get y'all familiar.
