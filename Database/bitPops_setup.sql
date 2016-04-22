drop table if exists Feeds;
drop table if exists FeedCategories;
drop table if exists Categories;
drop table if exists FeedsOfCountries;
drop table if exists InternationalFeeds;
drop table if exists Users;
drop table if exists Comments;
drop table if exists FeedsOfUsers;

create table Feeds (
	feedID int NOT NULL AUTO_INCREMENT,
	feedName nvarchar(100),	
	feedURL varchar(400),
	feedImageStatus tinyint,
	isInternational boolean,
	popularity int,
	dateAdded date,
	constraint pkFeedID primary key (feedID, feedName)
	);

create table FeedCategories (
	feedID int,
	categoryID tinyint,
	constraint pkFeedCategory primary key (feedID, categoryID)
	);
	
create table Categories (
	categoryID tinyint,
	categoryName nvarchar(45),
	constraint pkFeedCategory primary key (categoryID, categoryName)
	);
	

create table FeedsOfCountries (
	feedID int,
	feedCountry nvarchar(50),
	constraint pkFeedCountry primary key (feedID, feedCountry)
	);

create table InternationalFeeds (
	feedID int,
	isInternational boolean,
	constraint pkFeedInternational primary key (feedID, isInternational)
	);

create table Users (
	userID int NOT NULL AUTO_INCREMENT,
	userName nvarchar(50),
	userLastName nvarchar(50),
	userEmail nvarchar(90) NOT NULL,
	fbUserID nvarchar(100),
	constraint pkUser primary key (userID,userEmail)
	);
	
create table Comments (
	userID int NOT NULL,
	URL nvarchar(1000),
	Comment nvarchar(1000)
	);
	
create table FeedsOfUsers (
	userID int NOT NULL,
	feedID int NOT NULL,
	constraint PKfeedOfUser primary key (userID,feedID)
	);
	
alter table FeedCategories add constraint fkFeedCategory
		foreign key (feedID) references Feeds (feedID);

alter table Categories add constraint fkCategory
		foreign key (CategoryID) references FeedCategories(categoryID);

alter table FeedsOfCountries add constraint fkCountry
		foreign key (feedID) references Feeds (feedID);

alter table InternationalFeeds add constraint fkIntFeeds
		foreign key (feedID) references Feeds (feedID);
		
alter table Comments add constraint fkUserComment
		foreign key (userID) references Users (userID);
		
alter table FeedsOfUsers add constraint fkFeedOfUser
		foreign key (userID) references Users (userID);
		
alter table FeedsOfUsers add constraint fkUserFeed
		foreign key (feedID) references Feeds (feedID);	


-- Categories
INSERT INTO Categories (categoryID, categoryName) VALUES
(1,'News'),
(2,'Music'),
(3,'Finance'),
(4,'Sports'),
(5,'Lifestyle'),
(6,'Photography'),
(7,'Entertainment'),
(8,'Food'),
(9,'Travel');



-- normal == 0
-- no_images_t1 == 1
-- unwanted_wrong_images == 2	
INSERT INTO `Feeds` (`feedID`, `feedImageStatus`, `feedName`, `feedURL`, `Popularity`, `dateAdded`) VALUES
(1, 1, 'CNN World', 'http://rss.cnn.com/rss/edition_world.rss', 1, '2015-05-17'),
(2, 0, 'ShiftDelete.Net', 'http://shiftdelete.net/rss' , 1, '2015-05-17'),
(4, 2, 'The Guardian | World', 'http://feeds.theguardian.com/theguardian/world/rss', 0, '2015-05-17'),
(3, 0, 'Android Central', 'http://feeds2.feedburner.com/androidcentral', 0, '2015-05-17'),
(5, 0, 'MTV', 'http://www.mtv.com/rss/news/news_full.jhtml', 0, '2015-05-17'),
(6, 0, 'Billboard | Hot 100', 'http://www.billboard.com/rss/charts/hot-100', 0, '2015-05-17'),
(7, 2, 'Reuters | Business', 'http://feeds.reuters.com/reuters/businessNews', 0, '2015-05-17'),
(8, 2, 'Telegraph | Finance', 'http://www.telegraph.co.uk/finance/rss', 0, '2015-05-17'),
(9, 1, 'ESPN Top News', 'http://sports.espn.go.com/espn/rss/news', 0, '2015-05-17'),
(10, 1, 'New York Times | U.S.', 'http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml',     0, '2015-05-21'),
(11, 1, 'New York Times | International', 'http://www.nytimes.com/services/xml/rss/nyt/InternationalHome.xml',     0, '2015-05-21'),
(12, 2, 'Mashable', 'http://feeds.feedburner.com/Mashable',     0, '2015-05-21'),
(13, 0, 'Lifehacker', 'http://feeds.gawker.com/lifehacker/full',     0, '2015-05-21'),
(14, 1, 'TMZ', 'http://www.tmz.com/rss.xml',     0, '2015-05-21'),
(15, 2, 'Telegraph | World', 'http://www.telegraph.co.uk/news/worldnews/rss',     0, '2015-05-21'),
(16, 2, 'Telegraph | U.K.', 'http://www.telegraph.co.uk/news/uknews/rss',   0, '2015-05-21'),
(17, 0, 'PCWorld', 'http://www.pcworld.com/index.rss',     0, '2015-05-21'),
(18, 1, 'Popular Science', 'http://www.popsci.com/rss.xml',     0, '2015-05-21'),
(19, 0, 'WIRED', 'http://feeds.wired.com/wired/index',     0, '2015-05-21'),
(20, 1, 'Business Insider', 'http://www.businessinsider.in/rss_section_feeds/2147477994.cms',     0, '2015-05-21'),
(21, 1, 'Nikon | Updates', 'http://nikon.com/rss/updates/index.rss',     0, '2015-05-21'),
(22, 1, 'USA Today | Top Stories', 'http://rssfeeds.usatoday.com/usatoday-NewsTopStories',     0, '2015-05-21'),
(23, 2, 'TechRepublic | U.S.', 'http://www.techrepublic.com/rssfeeds/articles/latest/',     0, '2015-05-22'),
(24, 1, 'CBC', 'http://rss.cbc.ca/lineup/topstories.xml',     0, '2015-05-22'),
(25, 1, 'BBC | World', 'http://newsrss.bbc.co.uk/rss/newsonline_uk_edition/world/rss.xml',     0, '2015-05-22'),
(26, 1, 'BBC | U.K.', 'http://newsrss.bbc.co.uk/rss/newsonline_uk_edition/uk/rss.xml',     0, '2015-05-22'),
(27, 2, 'TechCrunch', 'http://feeds.feedburner.com/techcrunch',     0, '2015-05-22'),
(28, 0, 'SimplyRecipes', 'http://feeds.feedburner.com/elise/simplyrecipes',     0, '2015-05-22'),
(29, 0, 'Boing Boing', 'http://feeds.feedburner.com/boingboing/ibag',     0, '2015-05-22'),
(30, 0, 'readWrite', 'http://feeds.feedburner.com/readwriteweb',     0, '2015-05-22'),
(31, 1, 'Dumb Little Man', 'http://feeds.feedburner.com/DumbLittleMan',     0, '2015-05-22'),
(32, 1, 'ABC News | World', 'http://feeds.abcnews.com/abcnews/internationalheadlines',     0, '2015-05-22'),
(33, 1, 'ABC News | U.S.', 'http://feeds.abcnews.com/abcnews/usheadlines',     0, '2015-05-22'),
(34, 2, 'CNET ', 'http://www.cnet.com/rss/all/',     0, '2015-05-22'),
(35, 1, 'Fox News', 'http://feeds.foxnews.com/foxnews/latest',     0, '2015-05-22'),
(36, 0, 'Sky News', 'http://feeds.skynews.com/feeds/rss/world.xml',     0, '2015-05-22'),
(37, 2, 'NDTV', 'http://feeds.feedburner.com/NDTV-LatestNews',     0, '2015-05-22'),
(38, 0, 'The Express | U.K.', 'http://www.express.co.uk/posts/rss/1/uk',     0, '2015-05-22'),
(39, 1, 'NPR News', 'http://www.npr.org/rss/rss.php?id=1001',     0, '2015-05-22'),
(40, 2, 'Education Week', 'http://feeds.feedburner.com/EducationWeekNewsAndInformationAboutEducationIssues',     0, '2015-05-22'),
(41, 0, 'Fstoppers', 'https://fstoppers.com/feed',     0, '2015-05-22'),
(42, 1, 'PopPhoto', 'http://www.popphoto.com/rss.xml?dom=pph&loc=header&lnk=rss',     0, '2015-05-22'),
(43, 0, 'Fashion & Style', 'http://www.fashionnstyle.com/rss/archives/all.xml',     0, '2015-05-27'),
(44, 1, 'GQ | Latest', 'http://www.gq.com/services/rss/feeds/latest.xml',     0, '2015-05-27'),
(45, 1, 'GQ | The Style Guy', 'http://www.gq.com/services/rss/feeds/styleguy.xml',     0, '2015-05-27'),
(47, 1, 'GQ | The GQ Eye', 'http://www.gq.com/style/blogs/the-gq-eye/rss.xml',     0, '2015-05-27'),
(46, 1, 'GQ | The Q', 'http://www.gq.com/blogs/the-q/rss.xml',     0, '2015-05-27'),
(48, 2, 'Glamour', 'http://feeds.glamour.com/glamour/glamour_all',     0, '2015-05-27'),
(49, 1, 'TeenVogue', 'http://www.teenvogue.com/services/rss/feeds/composite.xml',     0, '2015-05-27'),
(50, 2, 'InStyle', 'http://news.instyle.com/feed',     0, '2015-05-27'),
(51, 1, 'JustLuxe', 'http://www.justluxe.com/rss/rss-channels.php?sec=lifestyle',  0, '2015-05-27'),
(53, 1, 'just-style', 'https://www.just-style.com/alerts/rssarticle.aspx',     0, '2015-05-27'),
(52, 1, 'Style At Home', 'http://www.styleathome.com/feeds/editorial_feed.xml',    0, '2015-05-27'),
(54, 0, 'Galactiv', 'http://feeds.feedburner.com/galactivv',     0, '2015-05-28'),
(55, 0, 'TechnoBuffalo', 'http://feeds.feedburner.com/technobuffalo/rss',     0, '2015-06-02'),
(56, 0, 'Android Police', 'http://feeds.feedburner.com/AndroidPolice',     0, '2015-06-02'),
(57, 2, 'Huffington Post | U.S', 'http://www.huffingtonpost.com/feeds/index.xml',     0, '2015-06-02'),
(58, 1, 'The Atlantic', 'http://feeds.feedburner.com/TheAtlantic',     0, '2015-06-02'),
(59, 1, 'Sozcu', 'http://feeds.feedburner.com/com/sozcugazetesi',    0, '2015-06-02'),
(60, 1, 'NotAlwaysWorking.com', 'http://feeds.feedburner.com/NotAlwaysWorking',     0, '2015-07-16'),
(61, 0, 'Phandroid', 'http://feeds2.feedburner.com/AndroidPhoneFans',     0, '2015-07-18'),
(62, 1, 'PhoneArena', 'http://www.phonearena.com/feed',     0, '2015-07-18'),
(63, 0, 'Playstation.Blog', 'http://feeds.feedburner.com/psblog',     0, '2015-07-18'),
(64, 0, 'Pocketnow', 'http://feeds.feedburner.com/pocketnow',     0, '2015-07-18'),
(65, 1, 'Official Google Blog', 'http://googleblog.blogspot.com.tr/atom.xml',     0, '2015-07-18'),
(66, 1, 'Polygon', 'http://www.polygon.com/rss/index.xml',     0, '2015-07-18'),
(67, 1, 'The Verge', 'http://www.theverge.com/rss/frontpage',     0, '2015-07-18'),
(68, 0, 'WonderHowTo', 'http://www.wonderhowto.com/rss.xml',     0, '2015-07-18'),
(69, 1, 'WinBeta', 'http://www.winbeta.org/feedburner/rss.xml',     0, '2015-07-18'),
(70, 1, 'CinemaBlend', 'http://feeds.feedburner.com/cinemablendallthing',     0, '2015-07-18'),
(71, 0, 'DailyMail - The Guardian', 'http://www.dailymail.co.uk/articles.rss',     0, '2015-07-18'),
(72, 0, 'Engadget', 'http://www.engadget.com/rss.xml',     0, '2015-07-18'),
(73, 0, 'Gizmodo', 'http://feeds.gawker.com/gizmodo/full',     0, '2015-07-18'),
(74, 0, 'Kotaku', 'http://kotaku.com/rss',     0, '2015-07-18'),
(75, 1, 'XDA-Developers', 'http://xda-developers.com/rss',     0, '2015-07-18'),
(76, 0, 'car abcd', 'http://feeds.feedburner.com/gawker/wgkt',     0, '2016-01-24');
	
