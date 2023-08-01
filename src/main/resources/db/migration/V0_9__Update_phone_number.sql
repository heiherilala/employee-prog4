DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'phone_number_with_code_constraint'
    )
    THEN
ALTER TABLE phone_number
    ADD CONSTRAINT phone_number_with_code_constraint UNIQUE (number_code, number);
END IF;
END;
$$;