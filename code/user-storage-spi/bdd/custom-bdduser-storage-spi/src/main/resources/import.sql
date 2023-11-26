CREATE TABLE "user" (
  id VARCHAR(36) PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  passw VARCHAR(100) NOT NULL
);

INSERT INTO "user" (id, username, email, passw) VALUES ('1', 'user1', 'user1@example.com', '$2a$10$ybcHrYSSCKnRbtTeTDeTQOTwZSCD1ZQHxZvJiPkgzwbPpIFyiYX9e');
INSERT INTO "user" (id, username, email, passw) VALUES ('2', 'user2', 'user2@example.com', '$2a$10$ybcHrYSSCKnRbtTeTDeTQOTwZSCD1ZQHxZvJiPkgzwbPpIFyiYX9e');
INSERT INTO "user" (id, username, email, passw) VALUES ('3', 'user3', 'user3@example.com', '$2a$10$ybcHrYSSCKnRbtTeTDeTQOTwZSCD1ZQHxZvJiPkgzwbPpIFyiYX9e');
