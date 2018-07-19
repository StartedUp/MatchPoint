ALTER TABLE `matchpoint_test`.`event_registration`
DROP FOREIGN KEY `FK2ra3ocp6gfw2vbwybbooxxuml`,
DROP FOREIGN KEY `FKsiqqwnfa1t1rybxsi424kfxh1`;
ALTER TABLE `matchpoint_test`.`event_registration`
CHANGE COLUMN `user_dob` `user_dob` DATETIME NULL ,
CHANGE COLUMN `event_id` `event_id` INT(11) NULL ,
CHANGE COLUMN `user_id` `user_id` INT(11) NULL ;
ALTER TABLE `matchpoint_test`.`event_registration`
ADD CONSTRAINT `FK2ra3ocp6gfw2vbwybbooxxuml`
  FOREIGN KEY (`event_id`)
  REFERENCES `matchpoint_test`.`event` (`id`),
ADD CONSTRAINT `FKsiqqwnfa1t1rybxsi424kfxh1`
  FOREIGN KEY (`user_id`)
  REFERENCES `matchpoint_test`.`user` (`id`);
