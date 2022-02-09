INSERT INTO `gift_certificate`
VALUES (101, 'TattooLand', 'The certificate allows to you make a tattoo', 125.00, 92, '2022-01-20 21:00:00',
        '2022-04-20 21:00:00'),
       (102, 'Jump park', 'Free jumps at trampolines', 35.00, 30, '2022-03-15 21:30:00', '2022-06-15 21:30:00'),
       (103, 'Water park', 'Visit to the water park for 4 hours', 50.00, 30, '2022-02-10 15:45:00',
        '2022-05-10 15:45:00');

INSERT INTO `tag`
VALUES (101, 'Tattoo'),
       (102, 'Jumps'),
       (103, 'Entertainment'),
       (104, 'Swimming');


INSERT INTO `gift_tags`
VALUES (101, 101),
       (102, 102),
       (102, 103);