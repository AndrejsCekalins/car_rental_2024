INSERT INTO classifiers(title, description)
VALUES('CAR_TYPE', 'Car typ classifier');

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_OPTIMUM',
    'Car rent optimum car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_PREMIUM',
    'Car rent premium car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_VAN',
    'Car rent van car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_LUX',
    'Car rent luxury car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

 INSERT INTO classifier_values(
 	classifier_id,
     ic,
     description)
 SELECT
 	cl.id,
     'CAR_SPORT',
     'Car rent sport car type'
  FROM classifiers as cl
  WHERE cl.title = 'CAR_TYPE';


  INSERT INTO classifiers(title, description)
  VALUES('COUNTRY', 'Country classifier');

  INSERT INTO classifier_values(
  	classifier_id,
      ic,
      description)
  SELECT
  	cl.id,
      'LATVIA',
      'Country Latvia'
   FROM classifiers as cl
   WHERE cl.title = 'COUNTRY';

  INSERT INTO classifier_values(
  	classifier_id,
      ic,
      description)
  SELECT
  	cl.id,
      'SPAIN',
      'Country Spain'
   FROM classifiers as cl
   WHERE cl.title = 'COUNTRY';

  INSERT INTO classifier_values(
  	classifier_id,
      ic,
      description)
  SELECT
  	cl.id,
      'JAPAN',
      'Country Japan'
   FROM classifiers as cl
   WHERE cl.title = 'COUNTRY';


  INSERT INTO country_default_day_rate(country_ic, default_day_rate)
  VALUES('LATVIA', 1.00);

  INSERT INTO country_default_day_rate(country_ic, default_day_rate)
  VALUES('SPAIN', 2.50);

  INSERT INTO country_default_day_rate(country_ic, default_day_rate)
  VALUES('GERMANY', 3.50);


  INSERT INTO age_coefficient(age_from, age_to, coefficient)
  VALUES(18, 26, 2.0);

  INSERT INTO age_coefficient(age_from, age_to, coefficient)
  VALUES(27, 70, 1.0);

  INSERT INTO age_coefficient(age_from, age_to, coefficient)
  VALUES(71, 150, 1.5);


