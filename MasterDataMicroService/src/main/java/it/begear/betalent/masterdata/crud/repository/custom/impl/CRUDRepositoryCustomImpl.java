package it.begear.betalent.masterdata.crud.repository.custom.impl;

import it.begear.betalent.masterdata.MasterDataServiceApplication;
import it.begear.betalent.masterdata.commons.ISearchField;
import it.begear.betalent.masterdata.commons.SearchField;
import it.begear.betalent.masterdata.commons.SearchFieldCountOf;
import it.begear.betalent.masterdata.commons.SearchFieldOR;
import it.begear.betalent.masterdata.crud.converter.CustomBooleanConverter;
import it.begear.betalent.masterdata.dto.searchDto.commons.ESortDir;
import it.begear.betalent.masterdata.dto.searchDto.commons.SearchRequestDTO;
import it.begear.betalent.masterdata.utils.AnnotationUtils;
import it.begear.betalent.masterdata.utils.GenerateTextFilterUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CRUDRepositoryCustomImpl<SEARCH extends SearchRequestDTO, T> {

	private GenerateTextFilterUtil getGenerateTextFilterUtil() {
		return MasterDataServiceApplication.applicationContext.getBean(GenerateTextFilterUtil.class);
	}

	private EntityManager getEntityManager() {
		return MasterDataServiceApplication.applicationContext.getBean(EntityManager.class);
	}

	public Long countElements(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields) {
		return countElements(classRoot, searchInformation, fields, null);
	}

	public Long countElements(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields, String applicationAttr) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();

		// create criteria for Company
		CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class);

		// retrieve count
		final Root<T> root = criteriaCount.from(classRoot);

		Predicate pTextFilter = builder.conjunction(); // Always true predicate: used to ignore
		Predicate searchInformationFilter = builder.conjunction(); // Always true predicate: used to ignore
		List<Predicate> textFilterLikePredicates = new ArrayList<Predicate>();
		List<Predicate> searchInformationPredicates = new ArrayList<Predicate>();

		if (fields != null && !fields.isEmpty()) {
			for (ISearchField field : fields) {
				if (field instanceof SearchFieldOR) {
					addOrToPredicate(builder, root, (SearchFieldOR) field, searchInformation, searchInformationPredicates);
				} else if (field instanceof SearchField) {
					addToPredicate(builder, root, (SearchField) field, searchInformation, searchInformationPredicates, textFilterLikePredicates, null);
				} else if (field instanceof SearchFieldCountOf) {
					addToPredicateCount(classRoot, criteriaCount, builder, root, (SearchFieldCountOf) field, searchInformation, searchInformationPredicates, textFilterLikePredicates, null);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(textFilterLikePredicates)) {
			pTextFilter = builder.or(textFilterLikePredicates.toArray(new Predicate[textFilterLikePredicates.size()]));
			searchInformationPredicates.add(pTextFilter);
		}

		//if (applicationAttr != null) {
		//	Predicate pApplication = builder.and(builder.equal(root.get(applicationAttr).get(Applications.Fields.code), EApplication.TLCPE.name()));
		//	searchInformationPredicates.add(pApplication);
		//}

		if (CollectionUtils.isNotEmpty(searchInformationPredicates)) {
			searchInformationFilter = builder.and(searchInformationPredicates.toArray(new Predicate[searchInformationPredicates.size()]));
		}

		criteriaCount.select(builder.countDistinct(root)).where(builder.and(searchInformationFilter));

		return getEntityManager().createQuery(criteriaCount).getResultList().stream().findFirst().orElse(0L);
	}

	public Long countElementsNoDistinct(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields) {
		return countElementsNoDistinct(classRoot, searchInformation, fields, null);
	}

	public Long countElementsNoDistinct(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields, String applicationAttr) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();

		// create criteria for Company
		CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class);

		// retrieve count
		final Root<T> root = criteriaCount.from(classRoot);

		Predicate pTextFilter = builder.conjunction(); // Always true predicate: used to ignore
		Predicate searchInformationFilter = builder.conjunction(); // Always true predicate: used to ignore
		List<Predicate> textFilterLikePredicates = new ArrayList<Predicate>();
		List<Predicate> searchInformationPredicates = new ArrayList<Predicate>();

		if (fields != null && !fields.isEmpty()) {
			for (ISearchField field : fields) {
				if (field instanceof SearchFieldOR) {
					addOrToPredicate(builder, root, (SearchFieldOR) field, searchInformation, searchInformationPredicates);
				} else if (field instanceof SearchField) {
					addToPredicate(builder, root, (SearchField) field, searchInformation, searchInformationPredicates, textFilterLikePredicates, null);
				} else if (field instanceof SearchFieldCountOf) {
					addToPredicateCount(classRoot, criteriaCount, builder, root, (SearchFieldCountOf) field, searchInformation, searchInformationPredicates, textFilterLikePredicates, null);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(textFilterLikePredicates)) {
			pTextFilter = builder.or(textFilterLikePredicates.toArray(new Predicate[textFilterLikePredicates.size()]));
			searchInformationPredicates.add(pTextFilter);
		}

		//if (applicationAttr != null) {
		//	Predicate pApplication = builder.and(builder.equal(root.get(applicationAttr).get(Applications.Fields.code), EApplication.TLCPE.name()));
		//	searchInformationPredicates.add(pApplication);
		//}

		if (CollectionUtils.isNotEmpty(searchInformationPredicates)) {
			searchInformationFilter = builder.and(searchInformationPredicates.toArray(new Predicate[searchInformationPredicates.size()]));
		}

		criteriaCount.select(builder.count(root)).where(builder.and(searchInformationFilter));

		return getEntityManager().createQuery(criteriaCount).getResultList().stream().findFirst().orElse(0L);
	}

	private Order addToPredicateCount(Class<T> classRoot, CriteriaQuery criteriaQuery, CriteriaBuilder builder, Root<T> root, SearchFieldCountOf field, SEARCH searchInformation, List<Predicate> searchInformationPredicates, List<Predicate> textFilterLikePredicates, Order order) {
		String dtoField = field.getDtoField();
		String joinField = findPrimaryKey(classRoot);
		String subQueryJoinPath = field.getSubQueryJoinPath();

		Long value = null;
		try {
			if (field.getFieldValue() != null) {
				value = field.getFieldValue().longValue();
			} else if (StringUtils.isNotEmpty(dtoField)) {
				value = ((Number) PropertyUtils.getProperty(searchInformation, dtoField)).longValue();
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
		}

		if (value != null) {
			//Start subQuery
			final Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
			final Root<?> rootSubQuery = subQuery.from(field.getModelClass());
			subQuery.select(builder.countDistinct(rootSubQuery));
			List<Predicate> subQueryInformationPredicates = new ArrayList<Predicate>();
			subQueryInformationPredicates.add(builder.equal(getPath(root, joinField), getPath(rootSubQuery, subQueryJoinPath + "." + joinField)));
			if (CollectionUtils.isNotEmpty(field.getFields())) {
				for (ISearchField f : field.getFields()) {
					if (f instanceof SearchFieldOR) {
						addOrToPredicate(builder, rootSubQuery, (SearchFieldOR) f, searchInformation, subQueryInformationPredicates);
					} else if (f instanceof SearchField) {
						addToPredicate(builder, rootSubQuery, (SearchField) f, searchInformation, subQueryInformationPredicates, null, null);
					}
				}
			}
			subQuery.where(subQueryInformationPredicates.toArray(new Predicate[0]));
			//End subQuery

			if (SearchFieldCountOf.Condition.EQUALS.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.equal(subQuery.getSelection(), value));
			} else if (SearchFieldCountOf.Condition.NOT_EQUALS.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.notEqual(subQuery.getSelection(), value));
			} else if (SearchFieldCountOf.Condition.GREATER_THAN.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.greaterThan(subQuery.getSelection(), value));
			} else if (SearchFieldCountOf.Condition.GREATER_THAN_OR_EQUAL_TO.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.greaterThanOrEqualTo(subQuery.getSelection(), value));
			} else if (SearchFieldCountOf.Condition.LESS_THAN.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.lessThan(subQuery.getSelection(), value));
			} else if (SearchFieldCountOf.Condition.LESS_THAN_OR_EQUAL_TO.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.lessThanOrEqualTo(subQuery.getSelection(), value));
			} else {
				searchInformationPredicates.add(builder.equal(subQuery.getSelection(), value));
			}
		}

		return order;
	}

	private String findPrimaryKey(Class<T> classRoot) {
		return AnnotationUtils.findFirstByAnnotation(classRoot, Id.class).getName();
	}

	private Order addToPredicate(CriteriaBuilder builder, Root<?> root, SearchField field, SEARCH searchInformation, List<Predicate> searchInformationPredicates, List<Predicate> textFilterLikePredicates, Order order) {
		// SEARCH TEXT FILTER
		String textFilterLike = searchInformation != null ? searchInformation.getFullSearch() : null;

		String dtoField = field.getDtoField();
		String modelField = field.getModelField();
		Expression<String> fieldExpression = makeFieldExpression(root, modelField);
		Expression<? extends Comparable> orderExpression = (Expression<? extends Comparable>) getPath(root, modelField);

		if (textFilterLikePredicates != null && StringUtils.isNotEmpty(textFilterLike) && !SearchField.Condition.IN.equals(field.getCondition()) && !SearchField.Condition.NOT_IN.equals(field.getCondition()) && !SearchField.Condition.NOT_EQUALS.equals(field.getCondition())) {
			textFilterLikePredicates.add(getGenerateTextFilterUtil().generateTextLikePredicate(builder, fieldExpression, textFilterLike));
		}
		Object value = null;
		try {
			if (StringUtils.isEmpty(dtoField) && field.getFieldValue() != null) {
				value = field.getFieldValue();
			} else {
				value = PropertyUtils.getProperty(searchInformation, String.valueOf(dtoField));
			}
			if (value instanceof Boolean) {
				value = CustomBooleanConverter.instance.booleanToString((Boolean) value);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
		}
		if (value != null) {
			if (SearchField.Condition.EQUALS.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.equal(fieldExpression, value));
			} else if (SearchField.Condition.NOT_EQUALS.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.notEqual(fieldExpression, value));
			} else if (SearchField.Condition.IN.equals(field.getCondition())) {
				searchInformationPredicates.add(fieldExpression.in((Collection<?>) value));
			} else if (SearchField.Condition.NOT_IN.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.not(fieldExpression.in((Collection<?>) value)));
			} else if (SearchField.Condition.GREATER_THAN.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.greaterThan(orderExpression, (Comparable) value));
			} else if (SearchField.Condition.GREATER_THAN_OR_EQUAL_TO.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.greaterThanOrEqualTo(orderExpression, (Comparable) value));
			} else if (SearchField.Condition.LESS_THAN.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.lessThan(orderExpression, (Comparable) value));
			} else if (SearchField.Condition.LESS_THAN_OR_EQUAL_TO.equals(field.getCondition())) {
				searchInformationPredicates.add(builder.lessThanOrEqualTo(orderExpression, (Comparable) value));
			} else if (SearchField.Condition.NUMBER_LIKE.equals(field.getCondition())) {
				searchInformationPredicates.add(getGenerateTextFilterUtil().generateNumberLikePredicate(builder, fieldExpression, String.valueOf(value)));
			} else {
				searchInformationPredicates.add(getGenerateTextFilterUtil().generateTextLikePredicate(builder, fieldExpression, String.valueOf(value)));
			}
		}
		if (SearchField.Condition.ISNULL.equals(field.getCondition())) {
			searchInformationPredicates.add(builder.isNull(fieldExpression));
		} else if (SearchField.Condition.ISNOTNULL.equals(field.getCondition())) {
			searchInformationPredicates.add(builder.isNotNull(fieldExpression));
		}
		if (order != null && dtoField != null && searchInformation != null && dtoField.equals(searchInformation.getSortField())) {
			order = searchInformation.getSortDir().equals(ESortDir.ASC) ? builder.asc(orderExpression) : builder.desc(orderExpression);
		}
		return order;
	}

	private void addOrToPredicate(CriteriaBuilder builder, Root<?> root, SearchFieldOR searchFieldORs, SEARCH searchInformation, List<Predicate> searchInformationPredicates) {
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		for (SearchField searchFieldOR : searchFieldORs.getOrCriteria()) {
			addToPredicate(builder, root, searchFieldOR, searchInformation, orPredicates, null, null);
		}
		if (CollectionUtils.isNotEmpty(orPredicates)) {
			Predicate orPredicate = builder.or((Predicate[]) orPredicates.toArray(new Predicate[orPredicates.size()]));
			searchInformationPredicates.add(orPredicate);
		}
	}

	public List<T> readAllElements(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields) {
		return readAllElements(classRoot, searchInformation, fields, null, null);
	}

	public List<T> readAllElements(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields, String applicationAttr) {
		return readAllElements(classRoot, searchInformation, fields, null, applicationAttr);
	}

	public List<T> readAllElements(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields, List<String> entityGraphs) {
		return readAllElements(classRoot, searchInformation, fields, entityGraphs, null);
	}

	public List<T> readAllElements(Class<T> classRoot, SEARCH searchInformation, List<? extends ISearchField> fields, List<String> entityGraphs, String applicationAttr) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();

		// create criteria for Company
		CriteriaQuery<T> criteriaEntity = builder.createQuery(classRoot);

		// retrieve count
		final Root<T> root = criteriaEntity.from(classRoot);

		Predicate pTextFilter = builder.conjunction(); // Always true predicate: used to ignore
		Predicate searchInformationFilter = builder.conjunction(); // Always true predicate: used to ignore
		Order order = null;
		List<Predicate> textFilterLikePredicates = new ArrayList<Predicate>();
		List<Predicate> searchInformationPredicates = new ArrayList<Predicate>();

		if (fields != null && !fields.isEmpty()) {
			if (fields.get(0) instanceof SearchField) {
				SearchField firstField = (SearchField) fields.get(0);
				Expression<?> firstFieldExpression = getPath(root, firstField.getModelField());
				order = searchInformation != null && searchInformation.getSortDir().equals(ESortDir.DESC) ? builder.desc(firstFieldExpression) : builder.asc(firstFieldExpression);
			}
			for (ISearchField field : fields) {
				if (field instanceof SearchFieldOR) {
					addOrToPredicate(builder, root, (SearchFieldOR) field, searchInformation, searchInformationPredicates);
				} else if (field instanceof SearchField) {
					order = addToPredicate(builder, root, (SearchField) field, searchInformation, searchInformationPredicates, textFilterLikePredicates, order);
				} else if (field instanceof SearchFieldCountOf) {
					order = addToPredicateCount(classRoot, criteriaEntity, builder, root, (SearchFieldCountOf) field, searchInformation, searchInformationPredicates, textFilterLikePredicates, order);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(textFilterLikePredicates)) {
			pTextFilter = builder.or((Predicate[]) textFilterLikePredicates.toArray(new Predicate[textFilterLikePredicates.size()]));
			searchInformationPredicates.add(pTextFilter);
		}

		//if (applicationAttr != null) {
		//	Predicate pApplication = builder.and(builder.equal(root.get(applicationAttr).get(Applications.Fields.code), EApplication.TLCPE.name()));
		//	searchInformationPredicates.add(pApplication);
		//}

		if (CollectionUtils.isNotEmpty(searchInformationPredicates)) {
			searchInformationFilter = builder.and((Predicate[]) searchInformationPredicates.toArray(new Predicate[searchInformationPredicates.size()]));
		}

		if (order == null) {
			criteriaEntity.distinct(true).select(root).where(builder.and(searchInformationFilter));
		} else {
			criteriaEntity.distinct(true).select(root).where(builder.and(searchInformationFilter)).orderBy(order);
		}

		// RUN CRITERIA QUERY
		TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaEntity);
		if (CollectionUtils.isNotEmpty(entityGraphs)) {
			EntityGraph entityGraph = getEntityManager().createEntityGraph(classRoot);
			for (String key : entityGraphs) {
				String[] splitted = key.split("\\.");
				Subgraph subgraph = null;
				for (String split : splitted) {
					if (subgraph == null) {
						subgraph = entityGraph.addSubgraph(split);
					} else {
						subgraph = subgraph.addSubgraph(split);
					}
				}
			}
			typedQuery.setHint("javax.persistence.loadgraph", entityGraph);
		}
		if (searchInformation != null && searchInformation.getPageNum() != null && searchInformation.getPageSize() != null) {
			typedQuery.setFirstResult((searchInformation.getPageNum() - 1) * searchInformation.getPageSize());
			typedQuery.setMaxResults(searchInformation.getPageSize());
		}

		return typedQuery.getResultList();
	}

	private Expression<String> makeFieldExpression(Root<?> root, String modelField) {
		return makeFieldExpression(root, modelField, String.class);
	}

	protected <T, S> Path<?> getPath(From<T, S> root, String name) {
		int index = name.indexOf(".");
		if (index > 0) {
			String attribute = name.substring(0, index);
			From<S, ?> join = getJoin(attribute, root.getJoins());
			if (join == null) {
				join = root.join(attribute, JoinType.LEFT);
			}
			return getPath(join, name.substring(index + 1));
		} else {
			return root.get(name);
		}
	}

	private <T> Join<T, ?> getJoin(String name, Set<Join<T, ?>> joins) {
		for (Join<T, ?> join : joins) {
			if (join.getAttribute().getName().equals(name)) {
				return join;
			}
		}
		return null;
	}

	private <E extends Comparable> Expression<E> makeFieldExpression(Root<?> root, String modelField, Class<E> classes) {
		return getPath(root, modelField).as(classes);
	}

	public boolean existsByDes(Class<T> classRoot, String value, String field) {
		return existsByDes(classRoot, value, field, null);
	}

	public boolean existsByDes(Class<T> classRoot, String value, String field, String applicationAttr) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();

		// create criteria for Company
		CriteriaQuery<T> criteriaCount = builder.createQuery(classRoot);

		// retrieve count
		final Root<T> root = criteriaCount.from(classRoot);
		Predicate searchInformationFilter = builder.conjunction(); // Always true predicate: used to ignore
		List<Predicate> searchInformationPredicates = new ArrayList<Predicate>();
		searchInformationPredicates.add(builder.equal(makeFieldExpression(root, field), value));

		//if (applicationAttr != null) {
		//	Predicate pApplication = builder.and(builder.equal(root.get(applicationAttr).get(Applications.Fields.code), EApplication.TLCPE.name()));
		//	searchInformationPredicates.add(pApplication);
		//}

		if (CollectionUtils.isNotEmpty(searchInformationPredicates)) {
			searchInformationFilter = builder.and((Predicate[]) searchInformationPredicates.toArray(new Predicate[searchInformationPredicates.size()]));
		}

		criteriaCount.select(root).where(builder.and(searchInformationFilter));

		return (getEntityManager().createQuery(criteriaCount).getResultList().size() == 1);
	}
}
