package it.begear.betalent.masterdata.utils;

import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@Component
public class GenerateTextFilterUtil {
	private static final char ESCAPE_CHARACTER = '\\';
	private static final int MIN_LENGHT_TEXT_SEARCH = 1;

	/**
	 * Generate a valuable string for like criteria
	 *
	 * @param textFilter
	 * @return
	 */
	private String generateTextFilter(String textFilter) {
		return generateTextFilter(textFilter, true, true, true);
	}

	/**
	 * Generate a valuable string for like criteria
	 *
	 * @param textFilter
	 * @param forceLowerCase
	 * @param startWildchar
	 * @param endWildchar
	 * @return
	 */
	private String generateTextFilter(String textFilter, boolean forceLowerCase, boolean startWildchar, boolean endWildchar) {
		String result = null;
		if (textFilter != null) {
			textFilter = textFilter.trim().replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
			if (textFilter.length() >= MIN_LENGHT_TEXT_SEARCH) {
				result = textFilter;
				if (forceLowerCase) {
					result = result.toLowerCase();
				}
				if (startWildchar) {
					result = "%" + result;
				}
				if (endWildchar) {
					result = result + "%";
				}
			}
		}
		return result;
	}

	public Predicate generateTextLikePredicate(CriteriaBuilder builder, Expression<String> field, String textFilter) {
		return generateTextLikePredicate(builder, field, textFilter, true, true, true);
	}

	public Predicate generateNumberLikePredicate(CriteriaBuilder builder, Expression<String> field, String textFilter) {
		return generateTextLikePredicate(builder, field, textFilter, false, false, true);
	}

	public Predicate generateTextLikePredicate(CriteriaBuilder builder, Expression<String> field, String textFilter, boolean forceLowerCase, boolean startWildcard, boolean endWildcard) {
		if (forceLowerCase) {
			return builder.like(builder.lower(field), this.generateTextFilter(textFilter, true, startWildcard, endWildcard), ESCAPE_CHARACTER);
		} else {
			return builder.like(field, this.generateTextFilter(textFilter, false, startWildcard, endWildcard), ESCAPE_CHARACTER);
		}
	}
}
